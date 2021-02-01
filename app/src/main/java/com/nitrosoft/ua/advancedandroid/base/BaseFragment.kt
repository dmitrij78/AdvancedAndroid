package com.nitrosoft.ua.advancedandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nitrosoft.ua.advancedandroid.di.Injector
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import com.nitrosoft.ua.advancedandroid.view_model.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.*
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    companion object {
        const val FRAGMENT_INSTANCE_ID_KEY = "instance_id"
        private val TAG: String = createTag(BaseFragment::class.java.simpleName)
    }

    private val disposables: CompositeDisposable = CompositeDisposable()

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var screenLifecycleTasks: Set<@JvmSuppressWildcards ScreenLifecycleTask>

    init {
        if (this.arguments == null) {
            this.arguments = Bundle()
        }

        val arguments = this.arguments
        arguments?.putString(FRAGMENT_INSTANCE_ID_KEY, UUID.randomUUID().toString())

        this.arguments = arguments
    }

    override fun onAttach(context: Context) {
        Timber.tag(TAG).d("onAttach")

        Injector.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.tag(TAG).d("onCreateView")
        val view = bindView(inflater, container)
        onViewBound(view)

        disposables.addAll(*emptyList<Disposable>().toTypedArray())
        for (screenLifecycleTask in screenLifecycleTasks) {
            screenLifecycleTask.onEnterScope(view)
        }
        return view
    }

    override fun onDestroyView() {
        Timber.tag(TAG).d("onDestroyView")

        disposables.clear()

        for (screenLifecycleTask in screenLifecycleTasks) {
            screenLifecycleTask.onExitScope()
        }

        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

        for (screenLifecycleTask in screenLifecycleTasks) {
            screenLifecycleTask.onDestroy()
        }

        onViewUnBound()
    }

    abstract fun bindView(inflater: LayoutInflater, container: ViewGroup?): View

    protected open fun onViewBound(view: View) {}

    protected open fun onViewUnBound() {}

    @Deprecated("Method wil be removes", ReplaceWith("emptyList()"))
    protected open fun subscriptions(): List<Disposable> {
        return emptyList()
    }

    fun <T> observeLiveData(liveData: LiveData<T>, observer: Observer<T>) {
        liveData.observe(viewLifecycleOwner, observer)
    }

    protected inline fun <reified T : ViewModel> createViewModel(): T {
        return ViewModelProvider(this, viewModelFactory)[T::class.java]
    }
}
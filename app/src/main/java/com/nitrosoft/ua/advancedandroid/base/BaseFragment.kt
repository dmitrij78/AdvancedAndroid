package com.nitrosoft.ua.advancedandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.UUID

abstract class BaseFragment : Fragment() {

    companion object {
        const val FRAGMENT_INSTANCE_ID_KEY = "instance_id"
        private val TAG: String = createTag(BaseFragment::class.java.simpleName)
    }

    private val disposables: CompositeDisposable = CompositeDisposable()

    /*@Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var screenLifecycleTasks: Set<@JvmSuppressWildcards ScreenLifecycleTask>*/

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

/*        Injector.inject(this)*/

        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.tag(TAG).d("onCreateView")
        val view = inflater.inflate(layoutRes(), container, false)

        onViewBound(view)

        disposables.addAll(*emptyList<Disposable>().toTypedArray())
/*        for (screenLifecycleTask in screenLifecycleTasks) {
            screenLifecycleTask.onEnterScope(view)
        }*/
        return view
    }

    override fun onDestroyView() {
        Timber.tag(TAG).d("onDestroyView")

        disposables.clear()

/*
        for (screenLifecycleTask in screenLifecycleTasks) {
            screenLifecycleTask.onExitScope()
        }
*/

        super.onDestroyView()
    }

    abstract fun layoutRes(): Int

    protected open fun onViewBound(view: View) {}

    @Deprecated("Method wil be removes", ReplaceWith("emptyList()"))
    protected open fun subscriptions(): List<Disposable> {
        return emptyList()
    }
/*
    fun <T> observeLiveData(liveData: LiveData<T>, observer: Observer<T>) {
        liveData.observe(viewLifecycleOwner, observer)
    }

    protected inline fun <reified T : ViewModel> createViewModel(): T {
        return ViewModelProvider(this, viewModelFactory)[T::class.java]
    }*/
}
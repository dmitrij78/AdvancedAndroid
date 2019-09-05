package com.nitrosoft.ua.advancedandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.nitrosoft.ua.advancedandroid.di.Injector
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    companion object {
        private val TAG: String = "AdvancedAndroidApp." + BaseFragment::class.java.simpleName
    }

    private val disposables: CompositeDisposable = CompositeDisposable()

    private var unBinder: Unbinder? = null

    @Inject lateinit var screenLifecycleTasks: Set<@JvmSuppressWildcards ScreenLifecycleTask>

    override fun onAttach(context: Context) {
        Timber.tag(TAG).d("onAttach")

        Injector.inject(this)

        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.tag(TAG).d("onCreateView")
        val view = inflater.inflate(layoutRes(), container, false)
        unBinder = ButterKnife.bind(this, view)

        onViewBound(view)

        disposables.addAll(*subscriptions().toTypedArray())

        for (screenLifecycleTask in screenLifecycleTasks) {
            screenLifecycleTask.onEnterScope(view)
        }

        return view
    }

    override fun onDestroyView() {
        Timber.tag(TAG).d("onDestroyView")

        disposables.clear()
        unBinder?.unbind()

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

        if (activity?.isChangingConfigurations!!) {
            Injector.clear(this)
        }
    }

    abstract fun layoutRes(): Int

    protected open fun onViewBound(view: View) {}

    protected open fun subscriptions(): List<Disposable> {
        return emptyList()
    }
}
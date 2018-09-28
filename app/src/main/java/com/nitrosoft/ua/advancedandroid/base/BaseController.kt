package com.nitrosoft.ua.advancedandroid.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.di.Injector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BaseController : Controller() {

    private val tag: String = "AdvancedAndroidApp." + BaseController::class.java.simpleName

    private var injected = false
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onContextAvailable(context: Context) {
        Timber.tag(tag).d("onContextAvailable")
        if (!injected) {
            Injector.inject(this)
            injected = true
        }
        super.onContextAvailable(context)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        Timber.tag(tag).d("onAttach")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        Timber.tag(tag).d("onCreateView")
        val view = inflater.inflate(layoutRes(), container, false)
        onViewBound(view)

        for (s in subscriptions()) {
            disposables.add(s)
        }

        return view
    }

    override fun onDestroyView(view: View) {
        Timber.tag(tag).d("onDestroyView")
        disposables.clear()
    }

    override fun onDetach(view: View) {
        Timber.tag(tag).d("onDetach")
    }

    override fun onDestroy() {
        Timber.tag(tag).d("onDestroy")
    }

    @LayoutRes
    abstract fun layoutRes(): Int

    protected open fun subscriptions() : Array<Disposable> {
        return arrayOf()
    }

    protected open fun onViewBound(view: View) {}
}
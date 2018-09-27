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

abstract class BaseController : Controller() {

    private var injected = false
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onContextAvailable(context: Context) {
        if (!injected) {
            Injector.inject(this)
            injected = true
        }
        super.onContextAvailable(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(layoutRes(), container, false)
        onViewBound(view)

        for (s in subscriptions()) {
            disposables.add(s)
        }

        return view
    }

    override fun onDestroyView(view: View) {
        disposables.clear()
    }

    @LayoutRes
    abstract fun layoutRes(): Int

    protected open fun subscriptions() : Array<Disposable> {
        return arrayOf()
    }

    protected open fun onViewBound(view: View) {}
}
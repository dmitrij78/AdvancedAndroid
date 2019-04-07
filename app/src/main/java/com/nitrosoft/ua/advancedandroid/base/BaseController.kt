package com.nitrosoft.ua.advancedandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.di.Injector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber


abstract class BaseController : Controller {

    private var injected = false
    private val disposables: CompositeDisposable = CompositeDisposable()

    private var unBinder: Unbinder? = null

    constructor() : super()

    constructor(args: Bundle?) : super(args)

    override fun onContextAvailable(context: Context) {
        Timber.tag(TAG).d("onContextAvailable")
        if (!injected) {
            Injector.inject(this)
            injected = true
        }
        super.onContextAvailable(context)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        Timber.tag(TAG).d("onAttach")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        Timber.tag(TAG).d("onCreateView")
        val view = inflater.inflate(layoutRes(), container, false)
        unBinder = ButterKnife.bind(this, view)
        onViewBound(view)

        for (s in subscriptions()) {
            disposables.add(s)
        }

        return view
    }

    override fun onDestroyView(view: View) {
        Timber.tag(TAG).d("onDestroyView")
        disposables.clear()

        unBinder?.unbind()
        unBinder = null
    }

    override fun onDetach(view: View) {
        Timber.tag(TAG).d("onDetach")
    }

    override fun onDestroy() {
        Timber.tag(TAG).d("onDestroy")
    }

    @LayoutRes
    abstract fun layoutRes(): Int

    protected open fun subscriptions(): Array<Disposable> {
        return arrayOf()
    }

    protected open fun onViewBound(view: View) {}

    companion object {
        private val TAG: String = "AdvancedAndroidApp." + BaseController::class.java.simpleName
    }
}
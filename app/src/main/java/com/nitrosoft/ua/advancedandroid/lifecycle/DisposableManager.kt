package com.nitrosoft.ua.advancedandroid.lifecycle

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableManager {

    private val compositeDisposable = CompositeDisposable()

    fun add(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }

    fun disposable() {
        compositeDisposable.clear()
    }
}

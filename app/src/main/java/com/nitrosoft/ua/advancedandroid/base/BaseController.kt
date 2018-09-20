package com.nitrosoft.ua.advancedandroid.base

import android.content.Context
import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.di.Injector

abstract class BaseController : Controller() {

    private var injected = false

    override fun onContextAvailable(context: Context) {
        if (!injected) {
            Injector.inject(this)
            injected = true
        }
        super.onContextAvailable(context)
    }
}
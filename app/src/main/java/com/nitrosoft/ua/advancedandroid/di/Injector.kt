package com.nitrosoft.ua.advancedandroid.di

import android.app.Activity
import com.bluelinelabs.conductor.Controller

object Injector {

    fun inject(activity: Activity) {
        ActivityInjector.get(activity).inject(activity)
    }

    fun clear(activity: Activity) {
        ActivityInjector.get(activity).clear(activity)
    }

    fun inject(controller: Controller) {
        ScreenInjector.get(controller.activity).inject(controller)
    }

    fun clear(controller: Controller) {
        ScreenInjector.get(controller.activity).clear(controller)
    }
}
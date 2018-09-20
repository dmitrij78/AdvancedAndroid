package com.nitrosoft.ua.advancedandroid.di

import android.app.Activity

object Injector {

    fun inject(activity: Activity) {
        ActivityInjector.get(activity).inject(activity)
    }

    fun clear(activity: Activity) {
        ActivityInjector.get(activity).clear(activity)
    }
}
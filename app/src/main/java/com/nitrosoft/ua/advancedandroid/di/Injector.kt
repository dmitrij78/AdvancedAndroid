package com.nitrosoft.ua.advancedandroid.di

import android.app.Activity
import androidx.fragment.app.Fragment

object Injector {

    fun inject(activity: Activity) {
        ActivityInjector.get(activity).inject(activity)
    }

    fun clear(activity: Activity) {
        ActivityInjector.get(activity).clear(activity)
    }

    fun inject(fragment: Fragment) {
        ScreenFragmentInjector.get(fragment.activity).inject(fragment)
    }

    fun clear(fragment: Fragment) {
        ScreenFragmentInjector.get(fragment.activity).clear(fragment)
    }

}
package com.nitrosoft.ua.advancedandroid.di

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

object Injector {

    fun inject(activity: Activity) {
        AndroidInjection.inject(activity)
    }

    fun inject(fragment: Fragment) {
        AndroidSupportInjection.inject(fragment)
    }
}
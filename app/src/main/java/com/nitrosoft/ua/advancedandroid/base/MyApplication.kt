package com.nitrosoft.ua.advancedandroid.base

import android.app.Application
import com.nitrosoft.ua.advancedandroid.di.ActivityInjector
import javax.inject.Inject

class MyApplication : Application() {

    private lateinit var component : ApplicationComponent

    @Inject lateinit var activityInjector: ActivityInjector

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        component.inject(this)
    }
}
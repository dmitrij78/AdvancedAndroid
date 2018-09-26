package com.nitrosoft.ua.advancedandroid.base

import android.app.Application
import com.nitrosoft.ua.advancedandroid.BuildConfig
import com.nitrosoft.ua.advancedandroid.di.ActivityInjector
import timber.log.Timber
import javax.inject.Inject

class MyApplication : Application() {

    private lateinit var component: ApplicationComponent

    @Inject
    lateinit var activityInjector: ActivityInjector

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        component.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
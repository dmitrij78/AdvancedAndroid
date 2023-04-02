package com.nitrosoft.ua.advancedandroid.base

import android.app.Application
import com.nitrosoft.ua.advancedandroid.BuildConfig
import com.nitrosoft.ua.advancedandroid.di.ActivityInjector
import timber.log.Timber
import javax.inject.Inject

open class MyApplication : Application() {

//    protected lateinit var component: ApplicationComponent
//
//    @Inject lateinit var activityInjector: ActivityInjector
lateinit var activityInjector: ActivityInjector

    override fun onCreate() {
        super.onCreate()

//        component = initComponent()
//        component.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    protected open fun initComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
package com.nitrosoft.ua.advancedandroid

import android.app.Application
import com.nitrosoft.ua.advancedandroid.application.ApplicationComponent
import com.nitrosoft.ua.advancedandroid.application.ApplicationModule
import com.nitrosoft.ua.advancedandroid.application.DaggerApplicationComponent
import com.nitrosoft.ua.advancedandroid.di.ActivityInjector
import timber.log.Timber
import javax.inject.Inject

open class App : Application() {

    protected lateinit var component: ApplicationComponent

    @Inject
    lateinit var activityInjector: ActivityInjector

    override fun onCreate() {
        super.onCreate()

        component = initComponent()
        component.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    protected open fun initComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().context(this)
            .build()
    }
}
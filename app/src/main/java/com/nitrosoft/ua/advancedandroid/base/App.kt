package com.nitrosoft.ua.advancedandroid.base

import android.app.Application
import com.facebook.stetho.Stetho
import com.nitrosoft.ua.advancedandroid.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

open class App : Application(), HasAndroidInjector {

    protected lateinit var component: ApplicationComponent

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        component = initComponent()
        component.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }

    protected open fun initComponent(): ApplicationComponent {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
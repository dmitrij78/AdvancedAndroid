package com.nitrosoft.ua.advancedandroid.base

import android.app.Application
import com.facebook.stetho.Stetho
import com.nitrosoft.ua.advancedandroid.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class App : Application() {

/*    protected lateinit var component: ApplicationComponent

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>*/

    override fun onCreate() {
        super.onCreate()

/*        component = initComponent()
        component.inject(this)*/

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }

/*    protected open fun initComponent(): ApplicationComponent {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }*/
}
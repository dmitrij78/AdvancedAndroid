package com.nitrosoft.ua.advancedandroid.base

import androidx.test.core.app.ApplicationProvider
import com.nitrosoft.ua.advancedandroid.MyApplication
import com.nitrosoft.ua.advancedandroid.application.ApplicationComponent
import com.nitrosoft.ua.advancedandroid.application.ApplicationModule
import java.util.*

class TestApplication : MyApplication() {

    override fun initComponent(): ApplicationComponent {
        return DaggerTestApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        initTestLocale()
    }

    private fun initTestLocale() {
        val application: TestApplication =
                ApplicationProvider.getApplicationContext() as TestApplication
        application.resources.configuration.setLocale(Locale("en", "US"))
    }

    companion object {

        fun getComponent(): TestApplicationComponent {
            val application: TestApplication =
                    ApplicationProvider.getApplicationContext() as TestApplication

            return application.component as TestApplicationComponent
        }
    }
}
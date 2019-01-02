package com.nitrosoft.ua.advancedandroid.base

import androidx.test.InstrumentationRegistry

class TestApplication : MyApplication() {

    override fun initComponent(): ApplicationComponent {
        return DaggerTestApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    companion object {
        fun getComponent(): TestApplicationComponent {
            val application: TestApplication =
                    InstrumentationRegistry.getTargetContext().applicationContext as TestApplication
            return application.component as TestApplicationComponent
        }
    }
}
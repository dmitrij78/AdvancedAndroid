package com.nitrosoft.ua.advancedandroid.test

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.nitrosoft.ua.advancedandroid.base.TestApplication

@Suppress("unused")
class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}
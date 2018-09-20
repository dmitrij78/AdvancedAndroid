package com.nitrosoft.ua.advancedandroid.base

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(app: Application) {

    private val application: Application = app

    @Provides
    fun provideApplicationContext() : Context {
        return application
    }
}
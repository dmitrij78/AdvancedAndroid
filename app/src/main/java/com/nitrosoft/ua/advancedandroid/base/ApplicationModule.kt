package com.nitrosoft.ua.advancedandroid.base

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers

@Module
class ApplicationModule(private val app: Application) {

    @Provides
    fun provideApplicationContext(): Context {
        Schedulers.io()
        return app
    }
}
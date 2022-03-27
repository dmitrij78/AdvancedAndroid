package com.nitrosoft.ua.advancedandroid.activity.ui

import com.nitrosoft.ua.advancedandroid.ui.ActivityViewInterceptor
import dagger.Module
import dagger.Provides

@Module
object  ActivityViewInterceptorModule {

    @Provides
    fun provideActivityViewInterceptor(): ActivityViewInterceptor = ActivityViewInterceptor.DEFAULT
}
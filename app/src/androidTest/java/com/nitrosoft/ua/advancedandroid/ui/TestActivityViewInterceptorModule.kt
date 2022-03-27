package com.nitrosoft.ua.advancedandroid.ui

import com.nitrosoft.ua.advancedandroid.activity.ui.ActivityViewInterceptor
import dagger.Module
import dagger.Provides

@Module
abstract class TestActivityViewInterceptorModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideActivityViewInterceptor(): ActivityViewInterceptor {
            return ActivityViewInterceptor.DEFAULT
        }
    }
}
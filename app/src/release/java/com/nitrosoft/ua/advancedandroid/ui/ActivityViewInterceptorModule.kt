package com.nitrosoft.ua.advancedandroid.ui

import dagger.Module
import dagger.Provides

@Module
abstract class ActivityViewInterceptorModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideActivityViewInterceptor(): ActivityViewInterceptor {
            return ActivityViewInterceptor.DEFAULT
        }
    }
}
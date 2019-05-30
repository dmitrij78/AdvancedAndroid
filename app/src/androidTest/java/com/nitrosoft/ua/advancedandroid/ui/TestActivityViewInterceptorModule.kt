package com.nitrosoft.ua.advancedandroid.ui

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
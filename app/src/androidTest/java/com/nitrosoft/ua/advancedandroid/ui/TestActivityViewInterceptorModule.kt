package com.nitrosoft.ua.advancedandroid.ui

import dagger.Module

@Module
abstract class TestActivityViewInterceptorModule {

    @Module
    companion object {
        fun provideActivityViewInteceptor(): ActivityViewInterceptor {
            return ActivityViewInterceptor.DEFAULT
        }
    }
}
package com.nitrosoft.ua.advancedandroid.ui

import dagger.Binds
import dagger.Module

@Module
abstract class ActivityViewInterceptorModule {

    @Binds
    abstract fun bindsActivityViewInterceptor(interceptor: DebugActivityViewInterceptor): ActivityViewInterceptor
}
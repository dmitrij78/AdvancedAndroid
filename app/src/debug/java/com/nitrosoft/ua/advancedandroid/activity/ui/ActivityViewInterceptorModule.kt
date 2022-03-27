package com.nitrosoft.ua.advancedandroid.activity.ui

import dagger.Binds
import dagger.Module

@Module
interface ActivityViewInterceptorModule {

    @Binds
    fun bindsActivityViewInterceptor(interceptor: DebugActivityViewInterceptor): ActivityViewInterceptor
}
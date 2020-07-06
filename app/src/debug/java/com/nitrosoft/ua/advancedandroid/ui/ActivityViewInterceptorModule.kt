package com.nitrosoft.ua.advancedandroid.ui

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityViewInterceptorModule {
/*
    @Binds
    @ActivityScoped
    abstract fun bindsActivityViewInterceptor(interceptor: DebugActivityViewInterceptor): ActivityViewInterceptor*/

    @Provides
    fun provideActivityViewInterceptor(): ActivityViewInterceptor {
        return ActivityViewInterceptor.DEFAULT
    }
}

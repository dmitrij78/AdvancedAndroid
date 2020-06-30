package com.nitrosoft.ua.advancedandroid.ui

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
abstract class ActivityViewInterceptorModule {

    @Binds
    @ActivityScoped
    abstract fun bindsActivityViewInterceptor(interceptor: DebugActivityViewInterceptor): ActivityViewInterceptor
}

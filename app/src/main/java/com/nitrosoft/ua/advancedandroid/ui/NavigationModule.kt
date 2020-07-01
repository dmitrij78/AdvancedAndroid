package com.nitrosoft.ua.advancedandroid.ui

import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoSet

/*
import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet


@Module
abstract class NavigationModule {

    @Binds
    abstract fun bindScreenNavigator(navigator: DefaultScreenNavigator): ScreenNavigator

    @Binds
    @IntoSet
    abstract fun bindActivityLifecycleTask(navigator: DefaultScreenNavigator): ActivityLifecycleTask
}*/
@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    abstract fun bindScreenNavigator(navigator: DefaultScreenNavigator): ScreenNavigator

    @Binds
    @IntoSet
    abstract fun bindActivityLifecycleTask(navigator: DefaultScreenNavigator): ActivityLifecycleTask
}
package com.nitrosoft.ua.advancedandroid.ui

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
}
package com.nitrosoft.ua.advancedandroid.ui

import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class TestNavigationModule {

    @Binds
    abstract fun bindScreenNavigator(testScreenNavigator: TestScreenNavigator): ScreenNavigator

    @Binds
    @IntoSet
    abstract fun bindActivityLifecycleTask(navigator: TestScreenNavigator): ActivityLifecycleTask
}
package com.nitrosoft.ua.advancedandroid.ui

import com.nitrosoft.ua.advancedandroid.di.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class NavigationModule {

    @Binds
    @ActivityScope
    abstract fun provideScreenNavigator(navigator: DefaultScreenNavigator): ScreenNavigator
}
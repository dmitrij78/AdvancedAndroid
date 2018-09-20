package com.nitrosoft.ua.advancedandroid.ui

import dagger.Binds
import dagger.Module

@Module
abstract class NavigationModule {

    @Binds
    abstract fun provideScreenNavigator(navigator: DefaultScreenNavigator): ScreenNavigator
}
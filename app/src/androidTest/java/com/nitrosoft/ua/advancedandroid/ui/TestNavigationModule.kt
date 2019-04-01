package com.nitrosoft.ua.advancedandroid.ui

import dagger.Binds
import dagger.Module

@Module
abstract class TestNavigationModule {

    @Binds
    abstract fun bindScreenNavigator(testScreenNavigator: TestScreenNavigator): ScreenNavigator
}
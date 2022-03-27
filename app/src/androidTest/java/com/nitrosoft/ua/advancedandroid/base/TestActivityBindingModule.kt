package com.nitrosoft.ua.advancedandroid.base

import com.nitrosoft.ua.advancedandroid.activity.MainActivity
import com.nitrosoft.ua.advancedandroid.home.TestMainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [TestMainActivityComponent::class])
abstract class TestActivityBindingModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjector(factory: TestMainActivityComponent.Factory): AndroidInjector.Factory<*>
}
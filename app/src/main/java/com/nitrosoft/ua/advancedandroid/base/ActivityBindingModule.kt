package com.nitrosoft.ua.advancedandroid.base

import com.nitrosoft.ua.advancedandroid.home.MainActivity
import com.nitrosoft.ua.advancedandroid.home.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivityComponent::class])
internal abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    internal abstract fun provideMainActivityInjector(factory: MainActivityComponent.Factory): AndroidInjector.Factory<*>
}

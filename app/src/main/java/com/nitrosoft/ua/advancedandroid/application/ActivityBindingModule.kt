package com.nitrosoft.ua.advancedandroid.application

import com.nitrosoft.ua.advancedandroid.activity.MainActivity
import com.nitrosoft.ua.advancedandroid.activity.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivityComponent::class])
interface ActivityBindingModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    fun provideActivityInjector(factory: MainActivityComponent.Factory): AndroidInjector.Factory<*>
}

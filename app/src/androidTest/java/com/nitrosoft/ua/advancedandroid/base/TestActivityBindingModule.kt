package com.nitrosoft.ua.advancedandroid.base

import android.app.Activity
import com.nitrosoft.ua.advancedandroid.home.MainActivity
import com.nitrosoft.ua.advancedandroid.home.TestMainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    TestMainActivityComponent::class
])
abstract class TestActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjector(builder: TestMainActivityComponent.Builder): AndroidInjector.Factory<out Activity>
}
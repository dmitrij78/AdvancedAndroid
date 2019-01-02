package com.nitrosoft.ua.advancedandroid.home

import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.di.ControllerKey
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposComponent
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposController
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    TrendingReposComponent::class
])
abstract class TestScreenBindingModule {
    @Binds
    @IntoMap
    @ControllerKey(TrendingReposController::class)
    abstract fun bindTrendingReposInjector(builder: TrendingReposComponent.Builder): AndroidInjector.Factory<out Controller>
}
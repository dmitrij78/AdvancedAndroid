package com.nitrosoft.ua.advancedandroid.home

import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.details.RepoDetailsComponent
import com.nitrosoft.ua.advancedandroid.details.RepoDetailsController
import com.nitrosoft.ua.advancedandroid.di.ControllerKey
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposComponent
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposController
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    TrendingReposComponent::class,
    RepoDetailsComponent::class
])
abstract class MainScreenBindingModule {

    @Binds
    @IntoMap
    @ControllerKey(TrendingReposController::class)
    abstract fun bindTrendingReposInjector(builder: TrendingReposComponent.Builder): AndroidInjector.Factory<out Controller>

    @Binds
    @IntoMap
    @ControllerKey(RepoDetailsController::class)
    abstract fun bindRepoDetailInjector(builder: RepoDetailsComponent.Builder): AndroidInjector.Factory<out Controller>
}
package com.nitrosoft.ua.advancedandroid.home

import com.nitrosoft.ua.advancedandroid.details.RepoDetailsComponent
import com.nitrosoft.ua.advancedandroid.details.RepoDetailsController
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposComponent
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposController
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    TrendingReposComponent::class,
    RepoDetailsComponent::class
])
abstract class MainScreenBindingModule {

    @Binds
    @IntoMap
    @ClassKey(TrendingReposController::class)
    abstract fun bindTrendingReposInjector(factory: TrendingReposComponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(RepoDetailsController::class)
    abstract fun bindRepoDetailInjector(factory: RepoDetailsComponent.Factory): AndroidInjector.Factory<*>
}
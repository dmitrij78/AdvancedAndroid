package com.nitrosoft.ua.advancedandroid.home

import com.nitrosoft.ua.advancedandroid.details.RepoDetailsComponent
import com.nitrosoft.ua.advancedandroid.details.RepoDetailsFragment
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposComponent
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    TrendingReposComponent::class, RepoDetailsComponent::class
])
abstract class TestScreenBindingModule {

    @Binds
    @IntoMap
    @ClassKey(TrendingReposFragment::class)
    abstract fun bindTrendingReposInjector(factory: TrendingReposComponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(RepoDetailsFragment::class)
    abstract fun bindRepoDetailsInjector(factory: RepoDetailsComponent.Factory): AndroidInjector.Factory<*>
}
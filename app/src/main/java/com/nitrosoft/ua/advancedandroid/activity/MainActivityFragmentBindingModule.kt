package com.nitrosoft.ua.advancedandroid.activity

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
    TrendingReposComponent::class,
    RepoDetailsComponent::class
])
interface  MainActivityFragmentBindingModule {

    @Binds
    @IntoMap
    @ClassKey(TrendingReposFragment::class)
    fun bindTrendingReposInjector(factory: TrendingReposComponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(RepoDetailsFragment::class)
    fun bindRepoDetailsInjector(factory: RepoDetailsComponent.Factory): AndroidInjector.Factory<*>
}
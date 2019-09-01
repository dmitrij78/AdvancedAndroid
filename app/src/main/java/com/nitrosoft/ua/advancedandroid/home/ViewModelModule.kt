package com.nitrosoft.ua.advancedandroid.home

import androidx.lifecycle.ViewModel
import com.nitrosoft.ua.advancedandroid.trending.TrendingRepoViewModel2
import com.nitrosoft.ua.advancedandroid.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TrendingRepoViewModel2::class)
    abstract fun bindRepoViewModel(viewModel: TrendingRepoViewModel2): ViewModel
}
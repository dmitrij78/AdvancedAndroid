package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class TrendingReposScreenModule {

    @Binds
    @IntoSet
    abstract fun bindUiManager(trendingRepoUIManger: TrendingRepoUIManger): ScreenLifecycleTask
}
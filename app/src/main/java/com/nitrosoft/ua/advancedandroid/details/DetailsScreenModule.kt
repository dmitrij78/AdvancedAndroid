package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class DetailsScreenModule {

    @Binds
    @IntoSet
    abstract fun bindUiManager(trendingRepoUIManger: DetailsUIManger): ScreenLifecycleTask
}
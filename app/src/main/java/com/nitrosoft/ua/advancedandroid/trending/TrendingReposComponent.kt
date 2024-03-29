package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.base.ScreenModule
import com.nitrosoft.ua.advancedandroid.di.ScreenComponent
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ScreenScope
@Subcomponent(modules = [ScreenModule::class, TrendingReposScreenModule::class])
interface TrendingReposComponent : ScreenComponent<TrendingReposFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<TrendingReposFragment>
}
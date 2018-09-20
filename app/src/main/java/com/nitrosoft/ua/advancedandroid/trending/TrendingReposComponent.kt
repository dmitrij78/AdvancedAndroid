package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ScreenScope
@Subcomponent
interface TrendingReposComponent : AndroidInjector<TrendingReposController> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<TrendingReposController>() {
        //TODO Не реализовано
    }
}
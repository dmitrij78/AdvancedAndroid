package com.nitrosoft.ua.advancedandroid.home

import com.nitrosoft.ua.advancedandroid.di.ActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [MainScreenBindingModule::class])
interface MainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

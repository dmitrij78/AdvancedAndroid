package com.nitrosoft.ua.advancedandroid.home

import com.nitrosoft.ua.advancedandroid.di.ActivityScope
import com.nitrosoft.ua.advancedandroid.ui.ActivityViewInterceptorModule
import com.nitrosoft.ua.advancedandroid.ui.NavigationModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [
    MainScreenBindingModule::class,
    NavigationModule::class,
    ActivityViewInterceptorModule::class
])
interface MainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}

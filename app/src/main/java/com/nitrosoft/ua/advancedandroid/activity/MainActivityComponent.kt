package com.nitrosoft.ua.advancedandroid.activity

import com.nitrosoft.ua.advancedandroid.activity.ui.ActivityViewInterceptorModule
import com.nitrosoft.ua.advancedandroid.di.ActivityScope
import com.nitrosoft.ua.advancedandroid.ui.NavigationModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [
    MainActivityFragmentBindingModule::class,
    NavigationModule::class,
    ActivityViewInterceptorModule::class
])
interface MainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}

package com.nitrosoft.ua.advancedandroid.home

import com.nitrosoft.ua.advancedandroid.di.ActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [
    TestScreenBindingModule::class
])
interface TestMainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}
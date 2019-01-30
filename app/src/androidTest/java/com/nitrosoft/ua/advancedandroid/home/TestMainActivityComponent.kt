package com.nitrosoft.ua.advancedandroid.home

import com.nitrosoft.ua.advancedandroid.di.ActivityScope
import com.nitrosoft.ua.advancedandroid.ui.NavigationModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [
	TestScreenBindingModule::class,
	NavigationModule::class
])
interface TestMainActivityComponent : AndroidInjector<MainActivity> {

	@Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}
package com.nitrosoft.ua.advancedandroid.base

import com.nitrosoft.ua.advancedandroid.data.TestRepoServiceModule
import com.nitrosoft.ua.advancedandroid.networking.ServiceModule
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposControllerTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    TestActivityBindingModule::class,
    TestRepoServiceModule::class,
	ServiceModule::class
])
interface TestApplicationComponent : ApplicationComponent {

    fun inject(myApplication: TrendingReposControllerTest)
}
package com.nitrosoft.ua.advancedandroid.base

import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.data.TestRepoService
import com.nitrosoft.ua.advancedandroid.data.TestRepoServiceModule
import com.nitrosoft.ua.advancedandroid.networking.ServiceModule
import com.nitrosoft.ua.advancedandroid.ui.TestNavigationModule
import com.nitrosoft.ua.advancedandroid.ui.TestScreenNavigator
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    TestActivityBindingModule::class,
    TestRepoServiceModule::class,
    ServiceModule::class,
    TestNavigationModule::class
])
interface TestApplicationComponent : ApplicationComponent {

    fun screenNavigator(): TestScreenNavigator

    fun testRepoService(): TestRepoService

    fun repoRepository(): RepoRepository
}
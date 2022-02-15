package com.nitrosoft.ua.advancedandroid.application

import com.nitrosoft.ua.advancedandroid.App
import com.nitrosoft.ua.advancedandroid.base.ActivityBindingModule
import com.nitrosoft.ua.advancedandroid.data.RepoServiceModule
import com.nitrosoft.ua.advancedandroid.database.DatabaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ActivityBindingModule::class,
    RepoServiceModule::class,
    DatabaseModule::class
])
interface ApplicationComponent {

    fun inject(myApplication: App)
}
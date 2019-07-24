package com.nitrosoft.ua.advancedandroid.base

import com.nitrosoft.ua.advancedandroid.data.RepoServiceModule
import com.nitrosoft.ua.advancedandroid.database.DatabaseModule
import com.nitrosoft.ua.advancedandroid.networking.ServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ActivityBindingModule::class,
    ServiceModule::class,
    RepoServiceModule::class,
    DatabaseModule::class
])
interface ApplicationComponent {

    fun inject(myApplication: MyApplication)
}
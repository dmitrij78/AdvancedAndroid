package com.nitrosoft.ua.advancedandroid.base

import android.app.Application
import com.nitrosoft.ua.advancedandroid.database.DatabaseModule
import com.nitrosoft.ua.advancedandroid.repository.RepoServiceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    ActivityBindingModule::class,
    RepoServiceModule::class,
    DatabaseModule::class
])
interface ApplicationComponent {

    fun inject(application: App)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance app: Application): ApplicationComponent
    }
}
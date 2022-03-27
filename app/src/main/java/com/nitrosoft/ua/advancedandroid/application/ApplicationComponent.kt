package com.nitrosoft.ua.advancedandroid.application

import android.content.Context
import com.nitrosoft.ua.advancedandroid.App
import com.nitrosoft.ua.advancedandroid.data.RepoServiceModule
import com.nitrosoft.ua.advancedandroid.database.DatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ActivityBindingModule::class,
        RepoServiceModule::class,
        DatabaseModule::class
    ]
)
interface ApplicationComponent {

    fun inject(myApplication: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ApplicationComponent
    }
}
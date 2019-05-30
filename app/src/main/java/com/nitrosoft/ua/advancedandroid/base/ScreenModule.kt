package com.nitrosoft.ua.advancedandroid.base

import com.nitrosoft.ua.advancedandroid.di.ForScreen
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.DisposableManager
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds

@Module
abstract class ScreenModule {

    @Module
    companion object {

        @Provides
        @ScreenScope
        @JvmStatic
        @ForScreen
        fun provideDisposableManger(): DisposableManager {
            return DisposableManager()
        }
    }

    @Multibinds
    abstract fun screenLifecycleTasks(): Set<ScreenLifecycleTask>
}
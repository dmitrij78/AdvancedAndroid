package com.nitrosoft.ua.advancedandroid.base

import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import dagger.Module
import dagger.multibindings.Multibinds

@Module
abstract class ScreenModule {

    @Multibinds
    abstract fun screenLifecycleTasks(): Set<ScreenLifecycleTask>
}
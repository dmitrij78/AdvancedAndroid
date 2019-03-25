package com.nitrosoft.ua.advancedandroid.data

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class TestRepoServiceModule {

    @Binds
    abstract fun bindRepoService(service: TestRepoService): RepoService

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        @Named("network_scheduler")
        fun provideNetworkScheduler(): Scheduler {
            return Schedulers.trampoline()
        }
    }
}
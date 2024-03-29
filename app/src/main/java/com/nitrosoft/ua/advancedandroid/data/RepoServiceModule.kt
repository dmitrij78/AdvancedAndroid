package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.networking.ServiceModule
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
class RepoServiceModule {

    @Singleton
    @Provides
    fun provideRepoServices(retrofit: Retrofit): RepoService {
        return retrofit.create(RepoService::class.java)
    }

    @Provides
    @Named("network_scheduler")
    fun provideNetworkScheduler(): Scheduler {
        return Schedulers.io()
    }
}
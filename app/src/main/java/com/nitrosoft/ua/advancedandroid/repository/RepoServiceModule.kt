package com.nitrosoft.ua.advancedandroid.repository

import com.nitrosoft.ua.advancedandroid.data.RepoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepoServiceModule {

    @Singleton
    @Provides
    fun provideRepoServices(retrofit: Retrofit): RepoService {
        return retrofit.create(RepoService::class.java)
    }

    @Provides
    @Singleton
    @Named("network_scheduler")
    fun provideNetworkScheduler(): Scheduler {
        return Schedulers.io()
    }
}

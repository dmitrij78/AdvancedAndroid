package com.nitrosoft.ua.advancedandroid.data

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepoServiceModule {

    @Singleton
    @Provides
    fun provideRepoServices(retrofit: Retrofit): RepoService {
        return retrofit.create(RepoService::class.java)
    }
}
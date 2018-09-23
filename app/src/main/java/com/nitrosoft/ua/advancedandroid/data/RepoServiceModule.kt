package com.nitrosoft.ua.advancedandroid.data

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepoServiceModule {

    @Singleton
    @Provides
    fun provideRepoServices(retrofit: Retrofit): RepoSrevice {
        return retrofit.create(RepoSrevice::class.java)
    }
}
package com.nitrosoft.ua.advancedandroid.networking

import dagger.Module
import dagger.Provides
import okhttp3.Call
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module()
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp(): Call.Factory {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    @Named("base_url")
    fun provideBaseUrl(): String {
        return "https://api.github.com"
    }
}
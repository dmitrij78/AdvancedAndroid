package com.nitrosoft.ua.advancedandroid.networking

import dagger.Module
import dagger.Provides
import okhttp3.Call
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideOkHttp(): Call.Factory {
            return OkHttpClient.Builder().build()
        }

        @JvmStatic
        @Singleton
        @Provides
        @Named("base_url")
        fun provideBaseUrl(): String {
            return "https://api.github.com"
        }
    }
}
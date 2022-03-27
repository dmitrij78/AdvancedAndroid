package com.nitrosoft.ua.advancedandroid.networking

import dagger.Module
import dagger.Provides
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named

@Module
object NetworkModule {

    @JvmStatic
    @Provides
    fun provideOkHttp(mockInterceptor: MockInterceptor): Call.Factory {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(mockInterceptor)
            .build()
    }

    @JvmStatic
    @Provides
    @Named("base_url")
    fun provideBaseUrl(): String {
        return "https://api.github.com"
    }
}
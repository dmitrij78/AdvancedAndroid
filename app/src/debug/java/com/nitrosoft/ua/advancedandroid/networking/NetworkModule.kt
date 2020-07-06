package com.nitrosoft.ua.advancedandroid.networking

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    companion object {

        @Singleton
        @Provides
        fun provideOkHttp(mockInterceptor: MockInterceptor): Call.Factory {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(mockInterceptor)
                    .addInterceptor(StethoInterceptor())
                    .build()
        }

        @Singleton
        @Provides
        @Named("base_url")
        fun provideBaseUrl(): String {
            return "https://api.github.com"
        }
    }
}

package com.nitrosoft.ua.advancedandroid.networking

import com.nitrosoft.ua.advancedandroid.models.ApplicationJsonAdapterFactory
import com.nitrosoft.ua.advancedandroid.models.ZoneDatetimeAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ServiceModule {

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideMoshi(): Moshi {
            return Moshi.Builder()
                    .add(ApplicationJsonAdapterFactory)
                    .add(ZoneDatetimeAdapter())
                    .build()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideRetrofit(moshi: Moshi, callFactory: Call.Factory, @Named("base_url") baseUrl: String): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .callFactory(callFactory)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }
}

package com.nitrosoft.ua.advancedandroid.networking

/*
@Module
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
}*/

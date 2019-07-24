package com.nitrosoft.ua.advancedandroid.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "favorites-database")
                    .build()
        }
    }
}
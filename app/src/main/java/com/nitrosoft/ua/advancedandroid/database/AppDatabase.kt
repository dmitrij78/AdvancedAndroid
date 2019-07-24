package com.nitrosoft.ua.advancedandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nitrosoft.ua.advancedandroid.database.favorites.FavouriteContributor
import com.nitrosoft.ua.advancedandroid.database.favorites.FavouriteContributorDao

@Database(entities = [FavouriteContributor::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteContributorDao(): FavouriteContributorDao
}
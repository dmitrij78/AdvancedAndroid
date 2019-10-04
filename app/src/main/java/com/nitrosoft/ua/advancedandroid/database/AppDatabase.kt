package com.nitrosoft.ua.advancedandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nitrosoft.ua.advancedandroid.database.favorites.FavouriteContributor
import com.nitrosoft.ua.advancedandroid.database.favorites.FavouriteContributorDao
import com.nitrosoft.ua.advancedandroid.database.repos.RepositoriesDao
import com.nitrosoft.ua.advancedandroid.database.repos.Repository

@Database(
        version = 1,
        exportSchema = false,
        entities = [FavouriteContributor::class, Repository::class])
@TypeConverters(ZoneDateTimeTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repositoriesDao(): RepositoriesDao

    abstract fun favoriteContributorDao(): FavouriteContributorDao
}
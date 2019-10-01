package com.nitrosoft.ua.advancedandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nitrosoft.ua.advancedandroid.database.favorites.FavouriteContributor
import com.nitrosoft.ua.advancedandroid.database.favorites.FavouriteContributorDao
import com.nitrosoft.ua.advancedandroid.database.repos.RepositoriesDao
import com.nitrosoft.ua.advancedandroid.database.repos.Repository

@Database(entities = [FavouriteContributor::class, Repository::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repositoriesDao(): RepositoriesDao

    abstract fun favoriteContributorDao(): FavouriteContributorDao
}
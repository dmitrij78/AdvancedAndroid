package com.nitrosoft.ua.advancedandroid.database.repos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface RepositoriesDao {

    @Query("SELECT * from repositories")
    fun getRepositories(): Flowable<List<Repository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repos: List<Repository>)
}
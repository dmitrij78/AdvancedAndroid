package com.nitrosoft.ua.advancedandroid.database.repos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoriesDao {

    @Query("SELECT * from repositories")
    fun getRepositories(): Flowable<List<RepoEntity>>

    @Query("SELECT * from repositories")
    fun getRepositoriesLive(): LiveData<List<RepoEntity>>

    @Query("SELECT * from repositories")
    fun getRepositoriesFlow(): Flow<List<RepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)
}
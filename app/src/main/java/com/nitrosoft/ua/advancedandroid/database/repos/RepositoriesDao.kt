package com.nitrosoft.ua.advancedandroid.database.repos

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoriesDao {

    @Query("SELECT * from repositories")
    fun getRepositories(): Flow<List<RepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Query("DELETE from repositories")
    suspend fun deleteAllRepos()

    @Transaction
    suspend fun insertNewData(repos: List<RepoEntity>) {
        deleteAllRepos()
        insertRepos(repos)
    }
}
package com.nitrosoft.ua.advancedandroid.repository

import com.nitrosoft.ua.advancedandroid.base.createTag
import com.nitrosoft.ua.advancedandroid.data.*
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntity
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntityConverter
import com.nitrosoft.ua.advancedandroid.models.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class RepoDataSourceBinder @Inject constructor(
        private val rateLimiter: RateLimiter,
        private val appDatabase: AppDatabase,
        private val requesterProvider: Provider<RepoRequester>,
        private val converter: RepoEntityConverter
) : BaseRepositoryDataSourceBinder<List<RepoEntity>, List<Repo>>() {

    companion object {
        val TAG = createTag(RepoDataSourceBinder::class.java.simpleName)
    }

    private val dataKey: String = "repository_list"

    override suspend fun loadFromDb(): Flow<List<RepoEntity>> {
        Timber.tag(TAG).d("loadFromDb")
        return appDatabase.repositoriesDao().getRepositories()
    }

    override suspend fun fetchData(): DataWrapper<List<Repo>> {
        Timber.tag(TAG).d("fetchData")
        val requester = requesterProvider.get()
        return fetchData(Dispatchers.IO) {
            requester.getTrendingRepos()
        }
    }

    override suspend fun saveRequest(response: List<Repo>?) {
        Timber.tag(TAG).d("saveRequest")
        response?.let {
            val entities = response.map { converter.mapToEntity(it) }
            appDatabase.repositoriesDao().insertNewData(entities)
        }
    }

    override fun shouldFetch(data: List<RepoEntity>?): Boolean {
        val result = data == null || data.isEmpty() || rateLimiter.shouldFetch(dataKey)
        Timber.tag(TAG).d("shouldFetch. result: $result")
        return result
    }

    override fun onFetchFailed() {
        Timber.tag(TAG).d("onFetchFailed")
        rateLimiter.reset(dataKey)
    }
}
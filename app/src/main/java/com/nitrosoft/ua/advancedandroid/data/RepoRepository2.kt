package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.base.createTag
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntityConverter
import com.nitrosoft.ua.advancedandroid.models.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoRepository2 @Inject constructor(
        private val repoRequesterProvider: Provider<RepoRequester>,
        private val database: AppDatabase,
        private val entityConverter: RepoEntityConverter
) {
    companion object {
        private val TAG: String = createTag(RepoRepository::class.java.simpleName)

        const val REPO_UPDATE_LIMIT = 10
        const val REPO_LIST_KEY = "repo_list"
    }

    private val rateLimiter = RateLimiter<String>(REPO_UPDATE_LIMIT, TimeUnit.SECONDS)

    fun getRepos(): Flow<RepoState<List<Repo>>> {
        return object : BaseDataBinder<List<Repo>, List<Repo>>() {

            override suspend fun loadFromDb(): Flow<List<Repo>> {
                return database.repositoriesDao().getRepositoriesFlow()
                        .map { it.map { e -> entityConverter.mapFromEntity(e) } }
            }

            override suspend fun fetchData(): DataResource<List<Repo>> {
                return repoRequesterProvider.get().getTrendingRepoCoroutine()
            }

            override suspend fun saveRequest(request: List<Repo>?) {
                if (request != null) {
                    val entities = request.map { model -> entityConverter.mapToEntity(model) }
                    database.repositoriesDao().insertRepos(entities)
                }
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return false //rateLimiter.shouldFetch(REPO_LIST_KEY)
            }

            override fun onFetchFailed() {
                rateLimiter.reset(REPO_LIST_KEY)
            }
        }.getData()
    }
}
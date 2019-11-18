package com.nitrosoft.ua.advancedandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.nitrosoft.ua.advancedandroid.base.createTag
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntity
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntityConverter
import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
        private val repoRequesterProvider: Provider<RepoRequester>,
        private val database: AppDatabase,
        private val entityConverter: RepoEntityConverter,
        @Named("network_scheduler") private val scheduler: Scheduler
) {
    private val cachedTrendingRepos: MutableList<Repo> = arrayListOf()
    private val cachedContributors: MutableMap<String, List<Contributor>> = mutableMapOf()

    companion object {
        private val TAG: String = createTag(RepoRepository::class.java.simpleName)

        const val REPO_UPDATE_LIMIT = 10
        const val REPO_LIST_KEY = "repo_list"
    }

    private val rateLimiter = RateLimiter<String>(REPO_UPDATE_LIMIT, TimeUnit.SECONDS)

    fun getTrendingRepos(): Single<List<Repo>> {
        return Maybe.concat(cachedTrendingRepos(), apiTrendingRepos())
                .firstOrError()
                .subscribeOn(scheduler)
    }

    fun getTrendingReposCoroutine(): LiveData<ResultWrapper<List<Repo>>> {
        return object : RemoteDataSourceBinderCoroutine<List<Repo>>(Dispatchers.IO) {

            override suspend fun loadFromDb(): Flow<List<Repo>> {
                return database.repositoriesDao().getRepositoriesFlow()
                        .map { entities -> entities.map { entityConverter.mapFromEntity(it) } }
            }

            override suspend fun createDataSourceCall(): List<Repo> {
                return repoRequesterProvider.get().getTrendingRepoCoroutine()
            }

            override suspend fun saveRequest(data: List<Repo>?) {
                if (data != null && data.isNotEmpty()) {
                    database.repositoriesDao().insertRepos(data.map { entityConverter.mapToEntity(it) })
                }
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || rateLimiter.shouldFetch(REPO_LIST_KEY)
            }

            override fun onFetchFailed() {
                rateLimiter.reset(REPO_LIST_KEY)
            }
        }.asLiveData()
    }

    private fun loadFromDb(): LiveData<List<Repo>> {
        val repoDbLive = database.repositoriesDao().getRepositoriesLive()
        return Transformations.map(repoDbLive) { entities -> convertEntitiesToRepos(entities) }
    }

    private fun convertEntitiesToRepos(entities: List<RepoEntity>?): List<Repo> {
        return entities?.map { entityConverter.mapFromEntity(it) } ?: emptyList()
    }

    fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        return Maybe.concat(cachedRepo(repoOwner, repoName), apiRepo(repoOwner, repoName))
                .firstOrError()
                .subscribeOn(scheduler)
    }

    fun getContributors(url: String): Single<List<Contributor>> {
        return Maybe.concat(cachedContributors(url), apiContributors(url))
                .firstOrError()
                .subscribeOn(scheduler)
    }

    private fun cachedContributors(url: String): Maybe<List<Contributor>> {
        return Maybe.create {
            if (cachedContributors.contains(url)) {
                it.onSuccess(cachedContributors[url]!!)
            }
            it.onComplete()
        }
    }

    private fun apiContributors(url: String): Maybe<List<Contributor>> {
        return repoRequesterProvider.get().getContributors(url)
                .doOnSuccess {
                    cachedContributors[url] = it
                }
                .toMaybe()
    }

    private fun cachedTrendingRepos(): Maybe<List<Repo>> {
        return Maybe.create {
            if (cachedTrendingRepos.isNotEmpty()) {
                it.onSuccess(cachedTrendingRepos)
            }
            it.onComplete()
        }
    }

    private fun apiTrendingRepos(): Maybe<List<Repo>> {
        return repoRequesterProvider.get().getTrendingRepos()
                .doOnSuccess { repo ->
                    cachedTrendingRepos.clear()
                    cachedTrendingRepos.addAll(repo)
                }
                .toMaybe()
    }

    private fun cachedRepo(repoOwner: String, repoName: String): Maybe<Repo> {
        return Maybe.create {
            for (repo in cachedTrendingRepos) {
                if (repo.user.login == repoOwner && repo.name == repoName) {
                    it.onSuccess(repo)
                    break
                }
            }
            it.onComplete()
        }
    }

    private fun apiRepo(repoOwner: String, repoName: String): Maybe<Repo> {
        return repoRequesterProvider.get().getRepo(repoOwner, repoName)
                .toMaybe()
    }

    fun clearCache() {
        cachedTrendingRepos.clear()
        cachedContributors.clear()
    }
}
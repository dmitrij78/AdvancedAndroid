package com.nitrosoft.ua.advancedandroid.repository

import com.nitrosoft.ua.advancedandroid.base.createTag
import com.nitrosoft.ua.advancedandroid.data.RepoRequester
import com.nitrosoft.ua.advancedandroid.data.RepoState
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntity
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntityConverter
import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
        private val requesterProvider: Provider<RepoRequester>,
        private val database: AppDatabase,
        private val entityConverter: RepoEntityConverter,
        private val dataSourceBinder: RepoDataSourceBinder,
        @Named("network_scheduler") private val scheduler: Scheduler
) {
    private val cachedTrendingRepos: MutableList<Repo> = arrayListOf()
    private val cachedContributors: MutableMap<String, List<Contributor>> = mutableMapOf()

    companion object {
        private val TAG: String = createTag(RepoRepository::class.java.simpleName)

        const val REPO_UPDATE_LIMIT = 10
    }

    fun getTrendingRepos(): Flow<RepoState<List<RepoEntity>>> {
        return dataSourceBinder.getData()
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
        return requesterProvider.get().getContributors(url)
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

/*    private fun apiTrendingRepos(): Maybe<List<Repo>> {
        return repoRequesterProvider.get().getTrendingRepos()
                .doOnSuccess { repo ->
                    cachedTrendingRepos.clear()
                    cachedTrendingRepos.addAll(repo)
                }
                .toMaybe()
    }*/

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
        return requesterProvider.get().getRepo(repoOwner, repoName)
                .toMaybe()
    }

    fun clearCache() {
        cachedTrendingRepos.clear()
        cachedContributors.clear()
    }
}
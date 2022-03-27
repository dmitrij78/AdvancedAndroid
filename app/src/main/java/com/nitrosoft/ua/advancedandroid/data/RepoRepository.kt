package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val repoRequesterProvider: Provider<RepoRequester>,
    @Named("network_scheduler") private val scheduler: Scheduler
) {

    private val cachedTrendingRepos: MutableList<Repo> = arrayListOf()
    private val cachedContributors: MutableMap<String, List<Contributor>> = mutableMapOf()

    fun getTrendingRepos(): Single<List<Repo>> {
        return Maybe.concat(cachedTrendingRepos(), apiTrendingRepos())
            .firstOrError()
            .subscribeOn(scheduler)
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
        return Maybe.create { emitter ->
            if (cachedTrendingRepos.isNotEmpty()) {
                emitter.onSuccess(cachedTrendingRepos)
            }
            emitter.onComplete()
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
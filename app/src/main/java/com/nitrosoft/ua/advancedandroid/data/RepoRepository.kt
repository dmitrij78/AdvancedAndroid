package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(private val repoRequesterProvider: Provider<RepoRequester>) {

    private val cachedTrendingRepos: MutableList<Repo> = arrayListOf()
    private val cachedContributors: MutableMap<String, List<Contributor>> = mutableMapOf()

    fun getTrendingRepos(): Single<List<Repo>> {
        return Maybe.concat(cachedTrendingRepos(), apiTrendingRepos())
                .firstOrError()
                .subscribeOn(Schedulers.io())
    }

    fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        return Maybe.concat(cachedRepo(repoOwner, repoName), apiRepo(repoOwner, repoName))
                .firstOrError()
                .subscribeOn(Schedulers.io())
    }

    fun getContributors(url: String): Single<List<Contributor>> {
        return Maybe.concat(cachedContributors(url), apiContributors(url))
                .firstOrError()
                .subscribeOn(Schedulers.io())
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
            if (!cachedTrendingRepos.isEmpty()) {
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
}
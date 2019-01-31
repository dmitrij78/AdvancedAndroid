package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import io.reactivex.MaybeOnSubscribe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(private val repoRequesterProvider: Provider<RepoRequester>) {

    private val cachedTrendingRepos: MutableList<Repo> = arrayListOf()

    fun getTrendingRepos(): Single<List<Repo>> {
        return Maybe.concat(cachedTrendingRepos(), apiTrendingRepos())
                .firstOrError()
    }

    fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        return Maybe.concat(cachedRepo(repoOwner, repoName), apiRepo(repoOwner, repoName))
                .firstOrError()
    }

    private fun cachedTrendingRepos(): Maybe<List<Repo>> {
        return Maybe.create(MaybeOnSubscribe(function = fun(it: MaybeEmitter<List<Repo>>) {
	        when (!cachedTrendingRepos.isEmpty()) {
                true -> it.onSuccess(cachedTrendingRepos)
                false -> it.onComplete()
            }
        }))
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
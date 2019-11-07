package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Single
import io.reactivex.functions.Function
import javax.inject.Inject

class RepoRequester @Inject constructor(private val repoService: RepoService) {

    fun getTrendingReposLive(): Single<DataResource<List<Repo>>> {
        return repoService.getTrendingRepos()
                .map(Function<TrendingReposResponse, DataResource<List<Repo>>> { response ->
                    val repos = response.repos
                    return@Function DataResource.Success(repos)
                })
                .onErrorReturn(Function { t ->
                    return@Function DataResource.Error<List<Repo>>(t)
                })
    }

    fun getTrendingRepos(): Single<List<Repo>> {
        return repoService.getTrendingRepos()
                .map(TrendingReposResponse::repos)
    }

    fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        return repoService.getRepo(repoOwner, repoName)
    }

    fun getContributors(url: String): Single<List<Contributor>> {
        return repoService.getContributors(url)
    }
}
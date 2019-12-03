package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoRequester @Inject constructor(private val repoService: RepoService) {

    suspend fun getTrendingRepoCoroutine(): DataResource<List<Repo>> {
        return safeApiCall(Dispatchers.IO) {
            repoService.getTrendingReposCoroutine().repos
        }
    }

    fun getTrendingRepos(): Single<List<Repo>> {
        return repoService.getTrendingRepos().map(TrendingReposResponse::repos)
    }

    fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        return repoService.getRepo(repoOwner, repoName)
    }

    fun getContributors(url: String): Single<List<Contributor>> {
        return repoService.getContributors(url)
    }

    private suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): DataResource<T> {
        return withContext(dispatcher) {
            try {
                DataResource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                DataResource.Error<T>(throwable)
            }
        }
    }
}
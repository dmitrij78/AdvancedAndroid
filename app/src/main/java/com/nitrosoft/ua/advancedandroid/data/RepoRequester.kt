package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class RepoRequester @Inject constructor(
    @Named("network") private val dispatcher: CoroutineDispatcher,
    private val repoService: RepoService
) {

    suspend fun getTrendingRepos(): List<Repo> {
        return fetch { repoService.getTrendingRepos().repos }
    }

    fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        return repoService.getRepo(repoOwner, repoName)
    }

    fun getContributors(url: String): Single<List<Contributor>> {
        return repoService.getContributors(url)
    }

    private suspend fun <T> fetch(block: suspend () -> T): T {
        return withContext(dispatcher) {
            block.invoke()
        }
    }
}
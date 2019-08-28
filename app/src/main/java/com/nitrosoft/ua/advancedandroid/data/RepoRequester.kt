package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Single
import javax.inject.Inject

class RepoRequester @Inject constructor(private val repoService: RepoService) {

    fun getTrendingRepos(): Single<List<Repo>> {
        val params = createGetTrendingReposRequestQuery(1, 10)
        return repoService.getTrendingRepos(params)
                .map(TrendingReposResponse::repos)
    }

    fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        return repoService.getRepo(repoOwner, repoName)
    }

    fun getContributors(url: String): Single<List<Contributor>> {
        return repoService.getContributors(url)
    }

    private fun createGetTrendingReposRequestQuery(page: Int, pageSize: Int): Map<String, String> {
        return mapOf(
                "page" to page.toString(),
                "per_page" to pageSize.toString(),
                "q" to "language:java",
                "order" to "desc",
                "sort" to "stars")
    }
}
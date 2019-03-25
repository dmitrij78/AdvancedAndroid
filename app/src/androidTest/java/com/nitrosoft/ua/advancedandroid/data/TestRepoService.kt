package com.nitrosoft.ua.advancedandroid.data

import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.test.TestUtils
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepoService @Inject constructor(private val testUtils: TestUtils) : RepoService {

    internal var sendError = false

    override fun getTrendingRepos(): Single<TrendingReposResponse> {
        if (!sendError) {
            val reposResponse = testUtils.loadJson<TrendingReposResponse>(
                    "mock/get_trending_repo.json",
                    TrendingReposResponse::class.java)

            return Single.just(reposResponse)
        }
        return Single.error(IOException())
    }

    override fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContributors(url: String): Single<List<Contributor>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
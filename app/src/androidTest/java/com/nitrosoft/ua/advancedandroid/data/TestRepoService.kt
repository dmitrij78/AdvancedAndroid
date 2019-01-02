package com.nitrosoft.ua.advancedandroid.data

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
}
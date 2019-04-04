package com.nitrosoft.ua.advancedandroid.data

import android.os.Handler
import android.os.Looper
import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.test.TestUtils
import com.squareup.moshi.Types
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepoService @Inject constructor(private val testUtils: TestUtils) : RepoService {

    companion object {
        const val FLAG_TRENDING_REPO = 1
        const val FLAG_GET_REPO = 2
        const val FLAG_GET_CONTRIBUTORS = 3
    }

    var errorFlags: Int = 0
    var holdFlags: Int = 0

    override fun getTrendingRepos(): Single<TrendingReposResponse> {
        if ((errorFlags and FLAG_TRENDING_REPO) == 0) {
            val reposResponse = testUtils.loadJson<TrendingReposResponse>(
                    "mock/get_trending_repo.json",
                    TrendingReposResponse::class.java)
            if ((holdFlags and FLAG_TRENDING_REPO) == FLAG_TRENDING_REPO) {
                return holdingSingle(reposResponse, FLAG_TRENDING_REPO)
            }
            return Single.just(reposResponse)
        }
        return Single.error(IOException())
    }

    override fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        if ((errorFlags and FLAG_GET_REPO) == 0) {
            val repo = testUtils.loadJson<Repo>(
                    "mock/get_repo.json",
                    Repo::class.java)
            if ((holdFlags and FLAG_GET_REPO) == FLAG_GET_REPO) {
                return holdingSingle(repo, FLAG_GET_REPO)
            }
            return Single.just(repo)
        }
        return Single.error(IOException())
    }

    override fun getContributors(url: String): Single<List<Contributor>> {
        if ((errorFlags and FLAG_GET_CONTRIBUTORS) == 0) {
            val contributors: List<Contributor> = testUtils.loadJson(
                    "mock/get_contributors.json",
                    Types.newParameterizedType(List::class.java, Contributor::class.java))
            if ((holdFlags and FLAG_GET_CONTRIBUTORS) == FLAG_GET_CONTRIBUTORS) {
                return holdingSingle(contributors, FLAG_GET_CONTRIBUTORS)
            }
            return Single.just(contributors)
        }
        return Single.error(IOException())
    }

    fun clearErrorFlags() {
        errorFlags = 0
    }

    fun clearHoldFlags() {
        holdFlags = 0
    }

    private fun <T> holdingSingle(result: T, flag: Int): Single<T> {
        return Single.create { emitter ->
            val handler = Handler(Looper.getMainLooper())
            object : Runnable {
                override fun run() {
                    if (flag and holdFlags == flag) {
                        handler.postDelayed(this, 50)
                    } else {
                        emitter.onSuccess(result)
                    }
                }
            }.run()
        }
    }
}

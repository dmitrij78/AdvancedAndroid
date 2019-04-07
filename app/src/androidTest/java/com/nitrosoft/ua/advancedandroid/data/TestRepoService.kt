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
class TestRepoService @Inject
internal constructor(private val testUtils: TestUtils) : RepoService {

    private var errorFlags: Int = 0
    private var holdFlags: Int = 0

    override fun getTrendingRepos(): Single<TrendingReposResponse> {
        if (errorFlags and FLAG_TRENDING_REPOS == 0) {
            val response = testUtils.loadJson<TrendingReposResponse>(
                    "mock/get_trending_repo.json",
                    TrendingReposResponse::class.java)

            return if (holdFlags and FLAG_TRENDING_REPOS == FLAG_TRENDING_REPOS) {
                holdingSingle(response, FLAG_TRENDING_REPOS)
            } else Single.just(response)
        }
        return Single.error(IOException())
    }

    override fun getRepo(repoOwner: String, repoName: String): Single<Repo> {
        if (errorFlags and FLAG_GET_REPO == 0) {
            val repo = testUtils.loadJson<Repo>("mock/get_repo.json", Repo::class.java)
            return if (holdFlags and FLAG_GET_REPO == FLAG_GET_REPO) {
                holdingSingle(repo, FLAG_GET_REPO)
            } else Single.just(repo)
        }
        return Single.error(IOException())
    }

    override fun getContributors(url: String): Single<List<Contributor>> {
        if (errorFlags and FLAG_GET_CONTRIBUTORS == 0) {
            val contributors = testUtils.loadJson<List<Contributor>>("mock/get_contributors.json", Types.newParameterizedType(List::class.java, Contributor::class.java))
            return if (holdFlags and FLAG_GET_CONTRIBUTORS == FLAG_GET_CONTRIBUTORS) {
                holdingSingle(contributors, FLAG_GET_CONTRIBUTORS)
            } else Single.just(contributors)
        }
        return Single.error(IOException())
    }

    fun setErrorFlags(errorFlags: Int) {
        this.errorFlags = errorFlags
    }

    fun clearErrorFlags() {
        this.errorFlags = 0
    }

    fun setHoldFlags(holdFlags: Int) {
        this.holdFlags = holdFlags
    }

    fun clearHoldFlags() {
        this.holdFlags = 0
    }

    private fun <T> holdingSingle(result: T, flag: Int): Single<T> {
        return Single.create { e ->
            val handler = Handler(Looper.getMainLooper())
            val holdRunnable = object : Runnable {
                override fun run() {
                    if (holdFlags and flag == flag) {
                        handler.postDelayed(this, 50)
                    } else {
                        e.onSuccess(result)
                    }
                }
            }
            holdRunnable.run()
        }
    }

    companion object {

        val FLAG_TRENDING_REPOS = 1
        val FLAG_GET_REPO = 2
        val FLAG_GET_CONTRIBUTORS = 4
    }
}

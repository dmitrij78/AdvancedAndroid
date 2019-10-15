package com.nitrosoft.ua.advancedandroid.data

import androidx.lifecycle.LiveData
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoLiveRepository @Inject constructor(

        @Named("network_scheduler") private val scheduler: Scheduler,
        private val repoRequesterProvider: Provider<RepoRequester>,
        private val rateLimiter: RateLimiter<String>) {

    fun getTrendingRepos(): NetworkBoundResource<List<Repo>, TrendingReposResponse> {
        return object : NetworkBoundResource<List<Repo>, TrendingReposResponse>() {
            override fun loadFromDb(): LiveData<List<Repo>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun saveCallResult(item: TrendingReposResponse?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createCall(): LiveData<TrendingReposResponse> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }
}
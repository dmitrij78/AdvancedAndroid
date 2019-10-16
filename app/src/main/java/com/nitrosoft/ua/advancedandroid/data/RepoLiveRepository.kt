package com.nitrosoft.ua.advancedandroid.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.database.Mapper
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntity
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoLiveRepository @Inject constructor(

        @Named("network_scheduler") private val backgroundScheduler: Scheduler,
        private val repoRequesterProvider: Provider<RepoRequester>,
        private val appDatabase: AppDatabase,
        private val rateLimiter: RateLimiter<String>,
        private val entityMapper: Mapper<RepoEntity, Repo>) {

    @SuppressLint("CheckResult")
    fun getTrendingRepos(): NetworkBoundResource<List<Repo>, ApiResource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, ApiResource<List<Repo>>>() {


            override fun loadFromDb(): LiveData<List<Repo>> {
                val liveData = MutableLiveData<List<Repo>>()
                appDatabase.repositoriesDao().getRepositories()
                        .subscribeOn(backgroundScheduler)
                        .flatMapIterable { e -> e }
                        .map { e -> entityMapper.mapFromEntity(e) }
                        .toList()
                        .toFlowable()
                        .subscribe({ repos -> liveData.postValue(repos) }, { liveData.postValue(emptyList()) })
                return liveData
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun saveCallResult(resource: ApiResource<List<Repo>>?) {
                //TODO
            }

            override fun createCall(): LiveData<ApiResource<List<Repo>>> {
                val result = MutableLiveData<ApiResource<List<Repo>>>()
                repoRequesterProvider.get().getTrendingReposLive()
                        .subscribeOn(backgroundScheduler)
                        .subscribe({ res -> result.postValue(res) }, {})
                return result
            }

        }
    }
}
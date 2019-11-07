package com.nitrosoft.ua.advancedandroid.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nitrosoft.ua.advancedandroid.base.createTag
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntityMapper
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Scheduler
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RepoLiveRepository @Inject constructor(
        @Named("network_scheduler") private val backgroundScheduler: Scheduler,
        private val repoRequesterProvider: Provider<RepoRequester>,
        private val appDatabase: AppDatabase,
        private val entityMapper: RepoEntityMapper) {

    companion object {
        private val TAG: String = createTag(RepoLiveRepository::class.java.simpleName)

        const val REPO_UPDATE_LIMIT = 10
        const val REPO_LIST_KEY = "repo_list"
    }

    private val rateLimiter = RateLimiter<String>(REPO_UPDATE_LIMIT, TimeUnit.SECONDS)

    @SuppressLint("CheckResult")
    fun getTrendingRepos(): LiveData<RepoState<List<Repo>>> {
        return object : DataSourceBinder<List<Repo>, DataResource<List<Repo>>>() {

            override fun loadFromDb(): LiveData<DataResource<List<Repo>>> {
                Timber.tag(TAG).d("loadFromDb")

                val result = MutableLiveData<DataResource<List<Repo>>>()
                appDatabase.repositoriesDao().getRepositories()
                        .subscribeOn(backgroundScheduler)
                        .map { entities ->
                            val repos = mutableListOf<Repo>()
                            for (repoEntity in entities) {
                                repos.add(entityMapper.mapFromEntity(repoEntity))
                            }
                            return@map repos
                        }
                        .subscribe({
                            result.postValue(DataResource.Success(it))
                        }, {
                            result.postValue(DataResource.Error(it))
                        })

                return result
            }

            override fun createCall(): LiveData<DataResource<DataResource<List<Repo>>>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun saveCallResult(requestType: DataResource<List<Repo>>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return false
                //return data == null || data.isEmpty() || rateLimiter.shouldFetch(REPO_LIST_KEY)
            }

/*            override fun loadFromDb(): LiveData<List<Repo>> {
                Timber.tag(TAG).d("loadFromDb")

                val liveData = MutableLiveData<List<Repo>>()
                appDatabase.repositoriesDao().getRepositories()
                        .subscribeOn(backgroundScheduler)
                        .map { entities ->
                            val repos = mutableListOf<Repo>()
                            for (repoEntity in entities) {
                                repos.add(entityMapper.mapFromEntity(repoEntity))
                            }
                            return@map repos
                        }
                        .subscribe({ repos -> liveData.postValue(repos) }, { liveData.postValue(emptyList()) })
                return liveData
            }*/

            /*override fun loadFromDb(): LiveData<RepoState<List<Repo>>> {

                Timber.tag(TAG).d("loadFromDb")

                val result:  MutableLiveData<RepoState<List<Repo>>> = MutableLiveData()

                appDatabase.repositoriesDao().getRepositories()
                        .subscribeOn(backgroundScheduler)
                        .map { entities ->
                            val repos = mutableListOf<Repo>()
                            for (repoEntity in entities) {
                                repos.add(entityMapper.mapFromEntity(repoEntity))
                            }
                            return@map repos
                        }
                        .subscribe(Consumer() {
                            it -> result.postValue(RepoState.Success(it)) = RepoState.Success(it)
                        })

                return result
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || data.isEmpty() || rateLimiter.shouldFetch(REPO_LIST_KEY)
            }

            override fun saveCallResult(requestType: DataResource<List<Repo>>?) {
                if (requestType?.data != null) {
                    Flowable.fromArray(requestType.data)
                            .subscribeOn(Schedulers.io())
                            .flatMapIterable { repos -> repos }
                            .map { repo -> entityMapper.mapToEntity(repo) }
                            .toList()
                            .subscribe({ entities -> appDatabase.repositoriesDao().insertRepos(entities) }, { })
                }
            }

            override fun createCall(): LiveData<DataResource<List<Repo>>> {
                val result = MutableLiveData<DataResource<List<Repo>>>()
                repoRequesterProvider.get().getTrendingReposLive()
                        .subscribeOn(backgroundScheduler)
                        .subscribe({ res -> result.postValue(res) }, {})
                return result
            }*/

        }.asLiveData()
    }
}
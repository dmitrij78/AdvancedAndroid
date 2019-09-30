package com.nitrosoft.ua.advancedandroid.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.data.Resource
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@ScreenScope
class TrendingRepoViewModel @Inject constructor(
        private val repoRepository: RepoRepository,
        private val screenNavigator: ScreenNavigator
) : ViewModel() {
    private val repoList: MutableLiveData<Resource<List<RepoListItem>>> by lazy {
        return@lazy MutableLiveData<Resource<List<RepoListItem>>>().also {
            fetchRepos(it)
        }
    }

    private var disposables = CompositeDisposable()

    companion object {
        val TAG: String = "AdvancedAndroidApp." + TrendingRepoViewModel::class.java.simpleName
    }

    override fun onCleared() {
        Timber.tag(TAG).d("onCleared")
        disposables.dispose()
    }

    fun onRepoListUpdate(): LiveData<Resource<List<RepoListItem>>> {
        return repoList
    }

    fun onRepoClicked(repo: Repo) {
        screenNavigator.goToRepoDetails(repo.user.login, repo.name)
    }

    private fun fetchRepos(liveData: MutableLiveData<Resource<List<RepoListItem>>>) {
        Timber.tag(TAG).d("fetchRepos")
        disposables.add(
                repoRepository.getTrendingRepos()
                        .doOnSubscribe { liveData.postValue(Resource.Loading()) }
                        .toObservable()
                        .flatMapIterable { it }
                        .map { RepoListItem(it) }
                        .toList()
                        .subscribe({ liveData.postValue(Resource.Success(it)) }, { liveData.postValue(Resource.Error(throwable = it)) })
        )
    }
}
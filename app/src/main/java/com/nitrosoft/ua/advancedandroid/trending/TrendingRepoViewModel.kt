package com.nitrosoft.ua.advancedandroid.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
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
    private val repoList: MutableLiveData<List<RepoListItem>> = MutableLiveData()
    private val loader: MutableLiveData<Boolean> = MutableLiveData()
    private val error: MutableLiveData<Int> = MutableLiveData()

    private var disposables = CompositeDisposable()

    companion object {
        val TAG: String = "AdvancedAndroidApp." + TrendingRepoViewModel::class.java.simpleName
    }

    init {
        Timber.tag(TAG).d("init")
        fetchRepos()
    }

    override fun onCleared() {
        Timber.tag(TAG).d("onCleared")
        disposables.dispose()
    }

    fun onRepoListUpdate(): LiveData<List<RepoListItem>> {
        return repoList
    }

    fun loading(): LiveData<Boolean> {
        return loader
    }

    fun onError(): LiveData<Int> {
        return error
    }

    fun onRepoClicked(repo: Repo) {
        screenNavigator.goToRepoDetails(repo.user.login, repo.name)
    }

    private fun fetchRepos() {
        Timber.tag(TAG).d("fetchRepos")
        disposables.add(
                repoRepository.getTrendingRepos()
                        .doOnSubscribe { loader.postValue(true) }
                        .doOnEvent { _, _ -> loader.postValue(false) }
                        .toObservable()
                        .flatMapIterable { it }
                        .map { RepoListItem(it) }
                        .toList()
                        .doOnSuccess { error.postValue(-1) }
                        .subscribe({ repoList.postValue(it) }, { error.postValue(R.string.api_error_repos) })
        )
    }
}
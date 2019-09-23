package com.nitrosoft.ua.advancedandroid.trending

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@ScreenScope
class TrendingRepoViewModel2 @Inject constructor(private val repoRepository: RepoRepository) : ViewModel() {
    private val repoList: MutableLiveData<List<RepoListItem>> = MutableLiveData()
    private val loader: MutableLiveData<Boolean> = MutableLiveData()
    private val error: MutableLiveData<Int> = MutableLiveData()

    private var disposables = CompositeDisposable()

    companion object {
        val TAG: String = "AdvancedAndroidApp." + TrendingRepoViewModel2::class.java.simpleName
    }

    init {
        Timber.tag(TAG).d("init")
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

    private fun fetchRepos() {
        Timber.tag(TAG).d("fetchRepos")

        disposables.add(
                repoRepository.getTrendingRepos()
                        .doOnSubscribe { loader.value = true }
                        .doOnEvent { _, _ -> loader.value = false }
                        .toObservable()
                        .flatMapIterable { it }
                        .map { RepoListItem(it) }
                        .toList()
                        .doOnSuccess { error.value = -1 }
                        .subscribe({ repoList.value = it }, { error.value = R.string.api_error_repos })
        )
    }
}
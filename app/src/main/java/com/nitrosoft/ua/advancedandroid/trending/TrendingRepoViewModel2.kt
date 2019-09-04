package com.nitrosoft.ua.advancedandroid.trending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.di.ForScreen
import com.nitrosoft.ua.advancedandroid.lifecycle.DisposableManager
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TrendingRepoViewModel2 @Inject constructor(
        @ForScreen private val disposableManager: DisposableManager,
        private val repoRepository: RepoRepository,
        private val screenNavigator: ScreenNavigator,
        private val recyclerDataSource: RecyclerDataSource
) : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Int>()

    init {
        loadRepos()
    }

    private fun loadRepos() {
        disposableManager.add(
                repoRepository.getTrendingRepos()
                        .doOnSubscribe { loading.value = true }
                        .doOnEvent { _, _ -> loading.value = false }
                        .toObservable()
                        .flatMapIterable { it }
                        .map { RepoListItem(it) }
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ recyclerDataSource.setData(it) }, { error.value = R.string.api_error_repos })
        )
    }
}
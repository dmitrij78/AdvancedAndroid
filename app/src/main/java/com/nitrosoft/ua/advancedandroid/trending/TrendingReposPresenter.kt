package com.nitrosoft.ua.advancedandroid.trending

import android.annotation.SuppressLint
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.di.ForScreen
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.DisposableManager
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoItem
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

@ScreenScope
class TrendingReposPresenter @Inject constructor(
        @ForScreen private val disposableManager: DisposableManager,
        private val viewModel: TrendingRepoViewModel,
        private val repoRepository: RepoRepository,
        private val screenNavigator: ScreenNavigator,
        private val recyclerDataSource: RecyclerDataSource) {

    fun onRepoClicked(repo: Repo) {
        screenNavigator.goToRepoDetails(repo.user.login, repo.name)
    }

    init {
        loadRepos()
    }

    @SuppressLint("CheckResult")
    private fun loadRepos() {
        disposableManager.add(
                repoRepository.getTrendingRepos()
                        .doOnSubscribe {
                            viewModel.loadingUpdated().accept(true)
                        }
                        .doOnEvent { _, _ -> viewModel.loadingUpdated().accept(false) }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                Consumer {
                                    val repoItems: MutableList<RepoItem> = mutableListOf()
                                    for (repo in it) {
                                        repoItems.add(RepoItem(repo))
                                    }

                                    recyclerDataSource.setData(repoItems)
                                },
                                viewModel.onError()
                        )
        )
    }
}
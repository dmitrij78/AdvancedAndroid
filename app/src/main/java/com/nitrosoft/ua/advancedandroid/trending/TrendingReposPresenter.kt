package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.data.RepoReuqester
import javax.inject.Inject

class TrendingReposPresenter @Inject constructor(private val viewModel: TrendingRepoViewModel, private val repoReuqester: RepoReuqester) {
    init {
        loadRepos()
    }

    private fun loadRepos() {
        repoReuqester.getTrendingRepos()
                .doOnSubscribe { viewModel.loadingUpdated().accept(true) }
                .doOnEvent { _, _ -> viewModel.loadingUpdated().accept(false) }
                .subscribe { d, t -> viewModel.requestUpdated() }
    }
}
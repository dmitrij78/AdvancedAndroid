package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.data.RepoRequester
import javax.inject.Inject

class TrendingReposPresenter @Inject constructor(private val viewModel: TrendingRepoViewModel, private val repoRequester: RepoRequester) {
    init {
        loadRepos()
    }

    private fun loadRepos() {
        repoRequester.getTrendingRepos()
                .doOnSubscribe { viewModel.loadingUpdated().accept(true) }
                .doOnEvent { _, _ -> viewModel.loadingUpdated().accept(false) }
                .subscribe { d, t -> viewModel.requestUpdated() }
    }
}
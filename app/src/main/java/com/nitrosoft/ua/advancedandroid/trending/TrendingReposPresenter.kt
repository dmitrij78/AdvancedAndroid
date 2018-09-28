package com.nitrosoft.ua.advancedandroid.trending

import android.annotation.SuppressLint
import com.nitrosoft.ua.advancedandroid.data.RepoRequester
import com.nitrosoft.ua.advancedandroid.models.Repo
import javax.inject.Inject

class TrendingReposPresenter @Inject constructor(private val viewModel: TrendingRepoViewModel, private val repoRequester: RepoRequester) : RepoClickListener {

    override fun onRepoClicked(repo: Repo) {}

    init {
        loadRepos()
    }

    @SuppressLint("CheckResult")
    private fun loadRepos() {
        repoRequester.getTrendingRepos()
                .doOnSubscribe { viewModel.loadingUpdated().accept(true) }
                .doOnEvent { _, _ -> viewModel.loadingUpdated().accept(false) }
                .subscribe(viewModel.requestUpdated(), viewModel.onError())
    }
}
package com.nitrosoft.ua.advancedandroid.trending

import android.annotation.SuppressLint
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.models.Repo
import javax.inject.Inject

@ScreenScope
class TrendingReposPresenter @Inject constructor(
        private val viewModel: TrendingRepoViewModel,
        private val repoRepository: RepoRepository) : RepoClickListener {

    override fun onRepoClicked(repo: Repo) {}

    init {
        loadRepos()
    }

    @SuppressLint("CheckResult")
    private fun loadRepos() {
        repoRepository.getTrendingRepos()
                .doOnSubscribe { viewModel.loadingUpdated().accept(true) }
                .doOnEvent { _, _ -> viewModel.loadingUpdated().accept(false) }
                .subscribe(viewModel.requestUpdated(), viewModel.onError())
    }
}
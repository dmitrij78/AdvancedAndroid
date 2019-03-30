package com.nitrosoft.ua.advancedandroid.details

import android.annotation.SuppressLint
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Named


@ScreenScope
class RepoDetailsPresenter @Inject constructor(
        @Named(RepoDetailsController.REPO_OWNER_KEY) private val repoOwner: String,
        @Named(RepoDetailsController.REPO_NAME_KEY) private val repoName: String,
        private val repository: RepoRepository,
        private val viewModel: RepoDetailsViewModel) {

    init {
        loadRepo()
    }

    @SuppressLint("CheckResult")
    private fun loadRepo() {
        repository.getRepo(repoOwner, repoName)
                .doOnSuccess(viewModel.processRepo())
                .doOnError(viewModel.detailsError())
                .flatMap { repository.getContributors(it.contributorsUrl).doOnError(viewModel.contributorsError()) }
                .subscribe(viewModel.processContributors(), Consumer { })
    }
}
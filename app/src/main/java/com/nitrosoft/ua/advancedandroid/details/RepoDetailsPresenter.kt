package com.nitrosoft.ua.advancedandroid.details

import android.annotation.SuppressLint
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.di.ForScreen
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.DisposableManager
import com.nitrosoft.ua.advancedandroid.models.Contributor
import com.nitrosoft.ua.advancedandroid.models.ContributorListItem
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Named


@ScreenScope
class RepoDetailsPresenter @Inject constructor(
        @Named(RepoDetailsFragment.REPO_OWNER_KEY) private val repoOwner: String,
        @Named(RepoDetailsFragment.REPO_NAME_KEY) private val repoName: String,
        @ForScreen private val disposableManager: DisposableManager,
        private val repository: RepoRepository,
        private val viewModel: RepoDetailsViewModel,
        private val contributorDataSource: RecyclerDataSource) {

    init {
        loadRepo()
    }

    @SuppressLint("CheckResult")
    private fun loadRepo() {
        disposableManager.add(
                repository.getRepo(repoOwner, repoName)
                        .doOnSuccess(viewModel.processRepo())
                        .doOnError(viewModel.detailsError())
                        .flatMap {
                            repository.getContributors(it.contributorsUrl)
                                    .doOnError(viewModel.contributorsError())
                        }
                        .toObservable()
                        .flatMapIterable { it }
                        .map { t: Contributor -> ContributorListItem(t) }
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess { contributorDataSource.setData(it) }
                        .subscribe(viewModel.contributorsLoaded(), Consumer { })
        )
    }
}
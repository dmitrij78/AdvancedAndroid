package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseController
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TrendingReposController : BaseController() {

    @Inject lateinit var presenter: TrendingReposPresenter
    @Inject lateinit var viewModel: TrendingRepoViewModel

    override fun layoutRes(): Int {
        return R.layout.view_trending_repo
    }

    override fun subscriptions(): Array<Disposable> {
        return Array(1) {
            viewModel.loading()
                    .subscribe()
        }
    }
}
package com.nitrosoft.ua.advancedandroid.trending

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class TrendingReposController : BaseController() {

    @Inject
    lateinit var presenter: TrendingReposPresenter

    @Inject
    lateinit var viewModel: TrendingRepoViewModel

    override fun layoutRes(): Int {
        return R.layout.view_trending_repo
    }

    override fun subscriptions(): Array<Disposable> {
        return Array(1) {
            viewModel.loading()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
        }
    }

    override fun onViewBound(view: View) {
        val repoList = view.findViewById<RecyclerView>(R.id.repoList)
        repoList.layoutManager = LinearLayoutManager(view.context)
    }
}
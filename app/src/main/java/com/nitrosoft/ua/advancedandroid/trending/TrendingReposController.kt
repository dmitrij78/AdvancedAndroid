package com.nitrosoft.ua.advancedandroid.trending

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.view_trending_repo.view.*
import javax.inject.Inject

class TrendingReposController : BaseController() {

    @Inject lateinit var presenter: TrendingReposPresenter
    @Inject lateinit var viewModel: TrendingRepoViewModel

    override fun layoutRes(): Int {
        return R.layout.view_trending_repo
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
                viewModel.loading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { loading ->
                            view?.loadingIndicator?.visibility = if (loading) View.VISIBLE else View.GONE
                            view?.repoList?.visibility = if (loading) View.GONE else View.VISIBLE
                            view?.errorText?.visibility = if (loading) View.GONE else view?.errorText?.visibility!!
                        },
                viewModel.repos()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { data ->
                            val adapter = view?.repoList?.adapter as RepoAdapter
                            adapter.setData(data.toMutableList())
                        },
                viewModel.error()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { errorStrRes ->
                            if (errorStrRes == -1) {
                                view?.errorText?.text = null
                                view?.errorText?.visibility = View.GONE
                            } else {
                                view?.errorText?.setText(errorStrRes)
                                view?.errorText?.visibility = View.VISIBLE
                                view?.repoList?.visibility = View.GONE
                            }
                        }
        )
    }

    override fun onViewBound(view: View) {
        view.repoList.layoutManager = LinearLayoutManager(view.context)
        view.repoList.adapter = RepoAdapter(presenter)
    }
}
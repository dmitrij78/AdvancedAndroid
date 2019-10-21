package com.nitrosoft.ua.advancedandroid.trending

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseFragment
import com.nitrosoft.ua.advancedandroid.data.DataResource
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.advancedandroid.view_model.ViewModelFactory
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.screen_trending_repo.view.*
import timber.log.Timber
import javax.inject.Inject

class TrendingReposFragment : BaseFragment() {

    @Inject lateinit var recyclerDataSource: RecyclerDataSource
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val loggerTag = TrendingReposFragment::class.java.simpleName

    private val repoListRelay = BehaviorRelay.create<List<Repo>>()

    companion object {
        fun newInstance(): TrendingReposFragment {
            return TrendingReposFragment()
        }
    }

    override fun layoutRes(): Int {
        return R.layout.screen_trending_repo
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel: TrendingRepoViewModel = createViewModel(viewModelFactory)
        observeViewModel(viewModel)
    }

    override fun onViewBound(view: View) {
        view.repoList.layoutManager = LinearLayoutManager(view.context)
        view.repoList.adapter = RecyclerAdapter(recyclerDataSource)
    }

    override fun subscriptions(): List<Disposable> {
        return arrayListOf(
                repoListRelay
                        .subscribeOn(Schedulers.io())
                        .flatMapIterable { it }
                        .map { RepoListItem(it) }
                        .toList()
                        .toFlowable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ data -> updateRepoList(data) }, { })
        )
    }

    private fun updateRepoList(data: List<RepoListItem>) {
        Timber.tag(loggerTag).d("repoListRelay. subscribe(). data.size=${data.size} ")
        recyclerDataSource.setData(data)
    }

    private fun observeViewModel(viewModel: TrendingRepoViewModel) {
        observeLiveData(viewModel.repoList, Observer { resource ->
            when (resource) {
                is DataResource.Success -> {
                    onLoading(false)
                    onError(-1)
                    updateRepos(resource.data)
                }
                is DataResource.Error -> {
                    onLoading(false)
                    onError(R.string.api_error_repos)
                }
                is DataResource.Loading -> {
                    if (resource.data != null) {

                    }
                    onLoading(true)
                }
            }
        })
    }

    private fun updateRepos(items: List<Repo>?) {
        if (items != null) {
            repoListRelay.accept(items)
        }
    }

    private fun onError(errorStrRes: Int) {
        if (errorStrRes == -1) {
            view?.errorText?.text = null
            view?.errorText?.visibility = View.GONE
        } else {
            view?.errorText?.setText(errorStrRes)
            view?.errorText?.visibility = View.VISIBLE
            view?.repoList?.visibility = View.GONE
        }
    }

    private fun onLoading(show: Boolean) {
        view?.loadingIndicator?.visibility = if (show) View.VISIBLE else View.GONE
        view?.repoList?.visibility = if (show) View.GONE else View.VISIBLE
        view?.errorText?.visibility = if (show) View.GONE else view?.errorText?.visibility!!
    }
}
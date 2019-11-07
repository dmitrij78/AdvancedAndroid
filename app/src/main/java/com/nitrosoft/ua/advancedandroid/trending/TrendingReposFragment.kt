package com.nitrosoft.ua.advancedandroid.trending

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxrelay2.BehaviorRelay
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseFragment
import com.nitrosoft.ua.advancedandroid.base.createTag
import com.nitrosoft.ua.advancedandroid.data.RepoState
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.advancedandroid.view_model.ViewModelFactory
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.screen_trending_repo.view.*
import timber.log.Timber
import javax.inject.Inject

class TrendingReposFragment : BaseFragment() {

    @Inject lateinit var recyclerDataSource: RecyclerDataSource
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val repoListRelay = BehaviorRelay.create<List<Repo>>()

    companion object {
        private val TAG: String = createTag(TrendingReposFragment::class.java.simpleName)

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
                /*repoListRelay
                        .subscribeOn(Schedulers.io())
                        .flatMapIterable { it }
                        .map { RepoListItem(it) }
                        .toList()
                        .toFlowable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ data -> updateRepoList(data) }, { })*/
        )
    }

    private fun observeViewModel(viewModel: TrendingRepoViewModel) {
        observeLiveData(viewModel.repoList, Observer { resource ->
            when (resource) {
                is RepoState.Success -> {
                    onResourceSuccess(resource)
                }
                is RepoState.Error -> {
                    onResourceError(resource)
                }
                is RepoState.Loading -> {
                    onResourceLoading(resource)
                }
            }
        })
    }

    private fun onResourceLoading(resource: RepoState.Loading<List<Repo>>) {
        Timber.tag(TAG).d("onResourceLoading. data.size=${resource.data?.size}")
        val data = resource.data
        if (data == null || data.isEmpty()) {
            onLoading(true)
        } else {
            onLoading(false)
            onError(-1)
            updateRepos(data)
        }
    }

    private fun onResourceError(resource: RepoState.Error<List<Repo>>) {
        Timber.tag(TAG).d("onResourceError. throwable=${resource.throwable?.message}")
        onLoading(false)
        onError(R.string.api_error_repos)
    }

    private fun onResourceSuccess(resource: RepoState.Success<List<Repo>>) {
        Timber.tag(TAG).d("onResourceSuccess. data.size=${resource.data?.size}")
        onLoading(false)
        onError(-1)
        updateRepos(resource.data)
    }

    private fun updateRepos(items: List<Repo>?) {
        Timber.tag(TAG).d("updateRepos. items: ${items?.size ?: "null"}")
        if (items != null) {
            val listItems: MutableList<RepoListItem> = mutableListOf()
            for (item in items) {
                listItems.add(RepoListItem((item)))
            }
            recyclerDataSource.setData(listItems)
        }
    }

    private fun onError(errorStrRes: Int) {
        Timber.tag(TAG).d("onError. errorStrRes: %d", errorStrRes)
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
        Timber.tag(TAG).d("onLoading. show: ${if (show) "true" else "false"}")
        view?.loadingIndicator?.visibility = if (show) View.VISIBLE else View.GONE
        view?.repoList?.visibility = if (show) View.GONE else View.VISIBLE
        view?.errorText?.visibility = if (show) View.GONE else view?.errorText?.visibility!!
    }
}
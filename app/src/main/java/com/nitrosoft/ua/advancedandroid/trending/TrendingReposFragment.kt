package com.nitrosoft.ua.advancedandroid.trending

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseFragment
import com.nitrosoft.ua.advancedandroid.data.DataResource
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.advancedandroid.view_model.ViewModelFactory
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import kotlinx.android.synthetic.main.screen_trending_repo.view.*
import javax.inject.Inject

class TrendingReposFragment : BaseFragment() {

    @Inject lateinit var recyclerDataSource: RecyclerDataSource
    @Inject lateinit var viewModelFactory: ViewModelFactory

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

    private fun observeViewModel(viewModel: TrendingRepoViewModel) {
        observeLiveData(viewModel.onRepoListUpdate(), Observer { resource ->
            when (resource) {
                is DataResource.Success -> {
                    onLoading(false)
                    onError(-1)
                    onSuccess(resource.data)
                }
                is DataResource.Error -> {
                    onLoading(false)
                    onError(R.string.api_error_repos)
                }
                is DataResource.Loading -> onLoading(true)
            }
        })
    }

    private fun onSuccess(data: List<RepoListItem>?) {
        data?.let { recyclerDataSource.setData(it) }
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
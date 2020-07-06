package com.nitrosoft.ua.advancedandroid.trending

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseFragment
import com.nitrosoft.ua.advancedandroid.base.createTag
import com.nitrosoft.ua.advancedandroid.data.RepoState
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntity
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.screen_trending_repo.errorText
import kotlinx.android.synthetic.main.screen_trending_repo.loadingIndicator
import kotlinx.android.synthetic.main.screen_trending_repo.repoList
import kotlinx.android.synthetic.main.screen_trending_repo.view.repoList
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TrendingReposFragment : BaseFragment() {

    @Inject lateinit var recyclerDataSource: RecyclerDataSource
    // @Inject lateinit var converter: RepoEntityConverter

    private val viewModel: TrendingRepoViewModel by viewModels()

    companion object {
        private val TAG: String = createTag(TrendingReposFragment::class.java.simpleName)

        fun newInstance(): TrendingReposFragment {
            return TrendingReposFragment()
        }
    }

    override fun layoutRes(): Int {
        return R.layout.screen_trending_repo
    }

    override fun onViewBound(view: View) {
        initList(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.repoList.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is RepoState.Success -> {
                    updateOnSuccess(resource.data)
                }
                is RepoState.Error -> {
                    updateOnError(resource.error)
                }
                is RepoState.Loading -> {
                    showLoader(resource.isLoading)
                }
                is RepoState.Syncing -> {
                    onStartSyncing(resource.isSyncing)
                }
            }
        })
    }

    private fun initList(view: View) {
        view.repoList.layoutManager = LinearLayoutManager(requireContext())
        view.repoList.adapter = RecyclerAdapter(recyclerDataSource)
    }

    private fun observeViewModel(viewModel: TrendingRepoViewModel) {
        /*observeLiveData(viewModel.repoList, Observer { resource ->
            when (resource) {
                is RepoState.Success -> {
                    updateOnSuccess(resource.data)
                }
                is RepoState.Error -> {
                    updateOnError(resource.error)
                }
                is RepoState.Loading -> {
                    showLoader(resource.isLoading)
                }
                is RepoState.Syncing -> {
                    onStartSyncing(resource.isSyncing)
                }
            }
        })*/
    }

    private fun onStartSyncing(isSyncing: Boolean) {
        Timber.tag(TAG).d("onStartSyncing. isSyncing: $isSyncing")
    }

    private fun updateOnError(throwable: Throwable) {
        Timber.tag(TAG).d("updateOnError. message: ${throwable.message}")
    }

    private fun updateOnSuccess(data: List<RepoEntity>) {
        Timber.tag(TAG).d("onResourceSuccess. data.size=${data.size}")
        onLoading(false)
        onError(-1)
        recyclerDataSource.setData(data.map { RepoListItem(it) })
    }

    private fun showLoader(isLoading: Boolean) {
        Timber.tag(TAG).d("showLoader. isLoading: $isLoading")
        /*val data = resource.data
        if (data == null || data.isEmpty()) {
            onLoading(true)
        } else {
            onLoading(false)
            onError(-1)
            updateRepos(data)
        }*/

        onLoading(isLoading)
    }

    private fun onError(errorStrRes: Int) {
        Timber.tag(TAG).d("onError. errorStrRes: %d", errorStrRes)
        if (errorStrRes == -1) {
            errorText.text = null
            errorText.visibility = View.GONE
        } else {
            errorText.setText(errorStrRes)
            errorText.visibility = View.VISIBLE
            repoList.visibility = View.GONE
        }
    }

    private fun onLoading(show: Boolean) {
        Timber.tag(TAG).d("onLoading. show: ${if (show) "true" else "false"}")
        loadingIndicator.visibility = if (show) View.VISIBLE else View.GONE
        repoList.visibility = if (show) View.GONE else View.VISIBLE
        errorText.visibility = if (show) View.GONE else errorText?.visibility!!
    }
}
package com.nitrosoft.ua.advancedandroid.trending

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseFragment
import com.nitrosoft.ua.advancedandroid.view_model.ViewModelFactory
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import kotlinx.android.synthetic.main.screen_trending_repo.view.*
import javax.inject.Inject

class TrendingReposFragment : BaseFragment() {

    @Inject lateinit var presenter: TrendingReposPresenter
    @Inject lateinit var viewModel: TrendingRepoViewModel
    @Inject lateinit var recyclerDataSource: RecyclerDataSource
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel2: TrendingRepoViewModel2

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

        viewModel2 = ViewModelProviders.of(this, viewModelFactory)[TrendingRepoViewModel2::class.java]
    }

    override fun observeVewModel(lifecycleOwner: LifecycleOwner) {
        viewModel2.onRepoListUpdate()
                .observe(lifecycleOwner, Observer { repoListItems ->
                    recyclerDataSource.setData(repoListItems)
                })
        viewModel2.loading()
                .observe(lifecycleOwner, Observer { loading ->
                    view?.loadingIndicator?.visibility = if (loading) View.VISIBLE else View.GONE
                    view?.repoList?.visibility = if (loading) View.GONE else View.VISIBLE
                    view?.errorText?.visibility = if (loading) View.GONE else view?.errorText?.visibility!!
                })
        viewModel2.onError()
                .observe(lifecycleOwner, Observer { errorStrRes ->
                    if (errorStrRes == -1) {
                        view?.errorText?.text = null
                        view?.errorText?.visibility = View.GONE
                    } else {
                        view?.errorText?.setText(errorStrRes)
                        view?.errorText?.visibility = View.VISIBLE
                        view?.repoList?.visibility = View.GONE
                    }
                })
    }
/*
    override fun subscriptions(): List<Disposable> {
        return arrayListOf(
                viewModel.loading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { loading ->
                            view?.loadingIndicator?.visibility = if (loading) View.VISIBLE else View.GONE
                            view?.repoList?.visibility = if (loading) View.GONE else View.VISIBLE
                            view?.errorText?.visibility = if (loading) View.GONE else view?.errorText?.visibility!!
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
    }*/

    override fun onViewBound(view: View) {
        view.repoList.layoutManager = LinearLayoutManager(view.context)
        view.repoList.adapter = RecyclerAdapter(recyclerDataSource)
    }
}
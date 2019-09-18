package com.nitrosoft.ua.advancedandroid.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseFragment
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.screen_repo_details.view.*
import javax.inject.Inject

class RepoDetailsFragment : BaseFragment() {

    @SuppressWarnings("unused")
    @Inject lateinit var presenter: RepoDetailsPresenter

    @Inject lateinit var viewModel: RepoDetailsViewModel
    @Inject lateinit var contributorsDataSource: RecyclerDataSource

    companion object {
        const val REPO_NAME_KEY = "repo_name"
        const val REPO_OWNER_KEY = "repo_owner"

        fun newInstance(repoName: String, repoOwner: String): Fragment {
            val args = Bundle()
            args.putString(REPO_NAME_KEY, repoName)
            args.putString(REPO_OWNER_KEY, repoOwner)

            val fragment = RepoDetailsFragment()
            fragment.arguments?.putAll(args)

            return fragment
        }
    }

    override fun onViewBound(view: View) {
        view.contributor_list.layoutManager = LinearLayoutManager(view.context)
        view.contributor_list.adapter = RecyclerAdapter(contributorsDataSource)
    }

    override fun subscriptions(): List<Disposable> {
        return arrayListOf(viewModel.details()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { details ->
                    if (details.loading) {
                        view?.loading_indicator?.visibility = View.VISIBLE
                        view?.content?.visibility = View.GONE
                        view?.tv_error?.visibility = View.GONE
                        view?.tv_error?.text = null
                    } else {
                        if (details.isSuccess()) {
                            view?.tv_error?.text = null
                        } else {

                            view?.tv_error?.setText(details.errorRes!!)
                        }
                        view?.loading_indicator?.visibility = View.GONE
                        view?.content?.visibility = if (details.isSuccess()) View.VISIBLE else View.GONE
                        view?.tv_error?.visibility = if (details.isSuccess()) View.GONE else View.VISIBLE
                        view?.tv_repo_name?.text = details.name
                        view?.tv_repo_description?.text = details.description
                        view?.tv_creation_date?.text = details.createDate
                        view?.tv_updated_date?.text = details.updateDate
                    }
                }, viewModel.contributors()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { contributorsDetails ->
                    if (contributorsDetails.loading) {
                        view?.contributor_loading_indicator?.visibility = View.VISIBLE
                        view?.contributor_list?.visibility = View.GONE
                        view?.tv_contributors_error?.visibility = View.GONE
                        view?.tv_contributors_error?.text = null
                    } else {
                        view?.contributor_loading_indicator?.visibility = View.GONE
                        view?.contributor_list?.visibility = if (contributorsDetails.isSuccess()) View.VISIBLE else View.GONE
                        view?.tv_contributors_error?.visibility = if (contributorsDetails.isSuccess()) View.GONE else View.VISIBLE
                        if (contributorsDetails.isSuccess()) {
                            view?.tv_contributors_error?.text = null
                        } else {
                            view?.tv_contributors_error?.setText(contributorsDetails.errorRes!!)
                        }
                    }
                })
    }

    override fun layoutRes(): Int {
        return R.layout.screen_repo_details
    }
}

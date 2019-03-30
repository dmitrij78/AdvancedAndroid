package com.nitrosoft.ua.advancedandroid.details

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class RepoDetailsController(args: Bundle?) : BaseController(args) {

    companion object {
        const val REPO_NAME_KEY = "repo_name"
        const val REPO_OWNER_KEY = "repo_owner"

        fun newInstance(name: String, owner: String): Controller {
            val bundle = Bundle()
            bundle.putString(REPO_NAME_KEY, name)
            bundle.putString(REPO_OWNER_KEY, owner)

            return RepoDetailsController(bundle)
        }
    }

    @Inject lateinit var viewModel: RepoDetailsViewModel
    @Inject lateinit var repoDetailsPresenter: RepoDetailsPresenter

    @BindView(R.id.tv_repo_name) lateinit var repoNameText: TextView
    @BindView(R.id.tv_repo_description) lateinit var repoDescriptionText: TextView
    @BindView(R.id.tv_creation_date) lateinit var createdDateText: TextView
    @BindView(R.id.tv_updated_date) lateinit var updatedDateText: TextView
    @BindView(R.id.contributor_list) lateinit var contributorList: RecyclerView
    @BindView(R.id.loading_indicator) lateinit var detailsLoadingView: View
    @BindView(R.id.contributor_loading_indicator) lateinit var contributorsLoadingView: View
    @BindView(R.id.content) lateinit var contentContainer: View
    @BindView(R.id.tv_error) lateinit var errorText: TextView
    @BindView(R.id.tv_contributors_error) lateinit var contributorsErrorText: TextView

    override fun layoutRes(): Int {
        return R.layout.screen_repo_details
    }

    override fun onViewBound(view: View) {
        contributorList.layoutManager = LinearLayoutManager(view.context)
        contributorList.adapter = ContributorAdapter()
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
                viewModel.details()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { details ->
                            if (details.loading) {
                                detailsLoadingView.visibility = View.VISIBLE
                                contentContainer.visibility = View.GONE
                                errorText.visibility = View.GONE
                                errorText.text = null
                            } else {
                                if (details.isSuccess()) {
                                    errorText.text = null
                                } else {
                                    details.errorRes?.let { it1 -> errorText.setText(it1) }
                                }
                                detailsLoadingView.visibility = View.GONE
                                contentContainer.visibility = when (details.isSuccess()) {
                                    true -> View.VISIBLE
                                    else -> View.GONE
                                }
                                errorText.visibility = when (details.isSuccess()) {
                                    true -> View.GONE
                                    else -> View.VISIBLE
                                }
                                repoNameText.text = details.name
                                repoDescriptionText.text = details.description
                                createdDateText.text = details.createDate
                                updatedDateText.text = details.updateDate
                            }
                        },
                viewModel.contributors()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { contributorDetails ->
                            if (contributorDetails.loading) {
                                contributorsLoadingView.visibility = View.VISIBLE
                                contributorList.visibility = View.GONE
                                contributorsErrorText.visibility = View.GONE
                                contributorsErrorText.text = null
                            } else {
                                contributorsLoadingView.visibility = View.GONE
                                contributorList.visibility = when (contributorDetails.isSuccess()) {
                                    true -> View.VISIBLE
                                    else -> View.GONE
                                }
                                contributorsErrorText.visibility = when (contributorDetails.isSuccess()) {
                                    true -> View.GONE
                                    else -> View.VISIBLE
                                }

                                if (contributorDetails.isSuccess()) {
                                    contributorsErrorText.text = null
                                    (contributorList.adapter as ContributorAdapter).setData(contributorDetails.contributors)
                                } else {
                                    contributorDetails.errorRes?.let { it1 -> contributorsErrorText.setText(it1) }
                                }
                            }
                        }
        )
    }
}
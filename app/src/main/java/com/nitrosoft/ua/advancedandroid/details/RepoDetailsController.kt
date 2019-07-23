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
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RepoDetailsController(bundle: Bundle) : BaseController(bundle) {

    @SuppressWarnings("unused")
    @Inject lateinit var presenter: RepoDetailsPresenter

    @Inject lateinit var viewModel: RepoDetailsViewModel
    @Inject lateinit var contributorsDataSource: RecyclerDataSource

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

    override fun onViewBound(view: View) {
        contributorList.layoutManager = LinearLayoutManager(view.context)
        contributorList.adapter = RecyclerAdapter(contributorsDataSource)
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(viewModel.details()
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

                            errorText.setText(details.errorRes!!)
                        }
                        detailsLoadingView.visibility = View.GONE
                        contentContainer.visibility = if (details.isSuccess()) View.VISIBLE else View.GONE
                        errorText.visibility = if (details.isSuccess()) View.GONE else View.VISIBLE
                        repoNameText.text = details.name
                        repoDescriptionText.text = details.description
                        createdDateText.text = details.createDate
                        updatedDateText.text = details.updateDate
                    }
                }, viewModel.contributors()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { contributorsDetails ->
                    if (contributorsDetails.loading) {
                        contributorsLoadingView.visibility = View.VISIBLE
                        contributorList.visibility = View.GONE
                        contributorsErrorText.visibility = View.GONE
                        contributorsErrorText.text = null
                    } else {
                        contributorsLoadingView.visibility = View.GONE
                        contributorList.visibility = if (contributorsDetails.isSuccess()) View.VISIBLE else View.GONE
                        contributorsErrorText.visibility = if (contributorsDetails.isSuccess()) View.GONE else View.VISIBLE
                        if (contributorsDetails.isSuccess()) {
                            contributorsErrorText.text = null
                        } else {
                            contributorsErrorText.setText(contributorsDetails.errorRes!!)
                        }
                    }
                })
    }

    override fun layoutRes(): Int {
        return R.layout.screen_repo_details
    }

    companion object {

        const val REPO_NAME_KEY = "repo_name"
        const val REPO_OWNER_KEY = "repo_owner"

        fun newInstance(repoName: String, repoOwner: String): Controller {
            val bundle = Bundle()
            bundle.putString(REPO_NAME_KEY, repoName)
            bundle.putString(REPO_OWNER_KEY, repoOwner)
            return RepoDetailsController(bundle)
        }
    }
}

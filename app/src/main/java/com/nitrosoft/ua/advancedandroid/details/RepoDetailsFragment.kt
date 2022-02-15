package com.nitrosoft.ua.advancedandroid.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseFragment
import com.nitrosoft.ua.advancedandroid.databinding.ScreenRepoDetailsBinding
import com.nitrosoft.ua.advancedandroid.databinding.ScreenTrendingRepoBinding
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RepoDetailsFragment : BaseFragment() {

    @SuppressWarnings("unused")
    @Inject
    lateinit var presenter: RepoDetailsPresenter

    @Inject
    lateinit var viewModel: RepoDetailsViewModel

    @Inject
    lateinit var contributorsDataSource: RecyclerDataSource

    private var _binding: ScreenRepoDetailsBinding? = null
    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScreenRepoDetailsBinding.inflate(inflater, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewBound(view: View) {
        binding.contributorList.layoutManager = LinearLayoutManager(view.context)
        binding.contributorList.adapter = RecyclerAdapter(contributorsDataSource)
    }

    override fun subscriptions(): List<Disposable> {
        return arrayListOf(viewModel.details()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { details ->
                if (details.loading) {
                    binding.loadingIndicator.visibility = View.VISIBLE
                    binding.content.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    binding.tvError.text = null
                } else {
                    if (details.isSuccess()) {
                        binding.tvError.text = null
                    } else {

                        binding.tvError.setText(details.errorRes!!)
                    }
                    binding.loadingIndicator.visibility = View.GONE
                    binding.content.visibility =
                        if (details.isSuccess()) View.VISIBLE else View.GONE
                    binding.tvError.visibility =
                        if (details.isSuccess()) View.GONE else View.VISIBLE
                    binding.tvRepoName.text = details.name
                    binding.tvRepoDescription.text = details.description
                    binding.tvCreationDate.text = details.createDate
                    binding.tvUpdatedDate.text = details.updateDate
                }
            }, viewModel.contributors()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { contributorsDetails ->
                if (contributorsDetails.loading) {
                    binding.contributorLoadingIndicator.visibility = View.VISIBLE
                    binding.contributorList.visibility = View.GONE
                    binding.tvContributorsError.visibility = View.GONE
                    binding.tvContributorsError.text = null
                } else {
                    binding.contributorLoadingIndicator.visibility = View.GONE
                    binding.contributorList.visibility =
                        if (contributorsDetails.isSuccess()) View.VISIBLE else View.GONE
                    binding.tvContributorsError.visibility =
                        if (contributorsDetails.isSuccess()) View.GONE else View.VISIBLE
                    if (contributorsDetails.isSuccess()) {
                        binding.tvContributorsError.text = null
                    } else {
                        binding.tvContributorsError.setText(contributorsDetails.errorRes!!)
                    }
                }
            })
    }

    override fun layoutRes(): View {
        return binding.root
    }
}

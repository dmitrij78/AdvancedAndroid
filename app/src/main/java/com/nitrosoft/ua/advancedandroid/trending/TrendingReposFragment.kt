package com.nitrosoft.ua.advancedandroid.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseFragment
import com.nitrosoft.ua.advancedandroid.databinding.ScreenTrendingRepoBinding
import com.nitrosoft.ua.poweradapter.adapter.RecyclerAdapter
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TrendingReposFragment : BaseFragment() {

    @Inject lateinit var presenter: TrendingReposPresenter
    @Inject lateinit var viewModel: TrendingRepoViewModel
    @Inject lateinit var recyclerDataSource: RecyclerDataSource

    private var _binding: ScreenTrendingRepoBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): TrendingReposFragment {
            return TrendingReposFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = ScreenTrendingRepoBinding.inflate(inflater, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun layoutRes(): View {
        return binding.root
    }

    override fun subscriptions(): List<Disposable> {
        return arrayListOf(
                viewModel.loading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { loading ->
                            binding.loadingIndicator.visibility = if (loading) View.VISIBLE else View.GONE
                            binding.repoList.visibility = if (loading) View.GONE else View.VISIBLE
                            binding.errorText.visibility = if (loading) View.GONE else binding.errorText.visibility
                        },
                viewModel.error()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { errorStrRes ->
                            if (errorStrRes == -1) {
                                binding.errorText.text = null
                                binding.errorText.visibility = View.GONE
                            } else {
                                binding.errorText.setText(errorStrRes)
                                binding.errorText.visibility = View.VISIBLE
                                binding.repoList.visibility = View.GONE
                            }
                        }
        )
    }


    override fun onViewBound(view: View) {
        binding.repoList.layoutManager = LinearLayoutManager(view.context)
        binding.repoList.adapter = RecyclerAdapter(recyclerDataSource)
    }
}
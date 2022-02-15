package com.nitrosoft.ua.advancedandroid.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.databinding.ViewRepoListItemBinding
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Provider

class RepoRenderer @Inject constructor(private val presenterProvider: Provider<TrendingReposPresenter>) :
    ItemRenderer<RepoListItem> {

    override fun layoutRes(): Int {
        return R.layout.view_repo_list_item
    }

    override fun createView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes(), parent, false)
        val binding = ViewRepoListItemBinding.bind(view)
        view.tag = ViewBinder(binding, presenterProvider.get())

        return view
    }

    override fun render(itemView: View, item: RecyclerItem) {
        val viewBinder = itemView.tag as ViewBinder
        viewBinder.bind(item as RepoListItem)
    }

    class ViewBinder(
        private val binding: ViewRepoListItemBinding,
        private val presenter: TrendingReposPresenter
    ) {

        private lateinit var repoListItem: RepoListItem

        init {
            binding.root.setOnClickListener {
                presenter.onRepoClicked(repoListItem.repo)
            }
        }

        fun bind(repoListItem: RepoListItem) {
            this.repoListItem = repoListItem

            binding.repoName.text = this.repoListItem.repo.name
            binding.repoDescription.text = this.repoListItem.repo.description
            binding.starCount.text =
                NumberFormat.getInstance().format(this.repoListItem.repo.starGazersCount)
            binding.forkCount.text =
                NumberFormat.getInstance().format(this.repoListItem.repo.forksCount)
        }
    }
}
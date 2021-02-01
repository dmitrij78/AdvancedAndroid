package com.nitrosoft.ua.advancedandroid.kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Provider

class RepoRenderer @Inject constructor(
        private val viewModelProvider: Provider<TrendingRepoViewModel>
) : ItemRenderer<RepoListItem> {

    override fun layoutRes(): Int {
        return R.layout.view_repo_list_item
    }

    override fun createView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes(), parent, false)
        view.tag = ViewBinder(view, viewModelProvider.get())

        return view
    }

    override fun render(itemView: View, item: RecyclerItem) {
        val viewBinder = itemView.tag as ViewBinder
        viewBinder.bind(item as RepoListItem)
    }

    @ExperimentalCoroutinesApi
    class ViewBinder(private val itemView: View,
                     private val viewModel: TrendingRepoViewModel) {

        private lateinit var repoListItem: RepoListItem

        private val repoName = itemView.findViewById<TextView>(R.id.repoName)
        private val repoDescription = itemView.findViewById<TextView>(R.id.repoDescription)
        private val starCount = itemView.findViewById<TextView>(R.id.repoDescription)
        private val forkCount = itemView.findViewById<TextView>(R.id.repoDescription)

        init {
            itemView.setOnClickListener {
                viewModel.onRepoClicked(repoListItem.repo)
            }
        }

        fun bind(repoListItem: RepoListItem) {
            this.repoListItem = repoListItem

            repoName.text = this.repoListItem.repo.name
            repoDescription.text = this.repoListItem.repo.description
            starCount.text = NumberFormat.getInstance().format(this.repoListItem.repo.starGazersCount)
            forkCount.text = NumberFormat.getInstance().format(this.repoListItem.repo.forksCount)
        }
    }
}
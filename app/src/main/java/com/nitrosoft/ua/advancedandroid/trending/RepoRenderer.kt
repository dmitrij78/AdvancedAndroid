package com.nitrosoft.ua.advancedandroid.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.models.RepoItem
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import kotlinx.android.synthetic.main.view_repo_list_item.view.*
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Provider

class RepoRenderer @Inject constructor(private val presenterProvider: Provider<TrendingReposPresenter>) : ItemRenderer<RepoItem> {

    override fun layoutRes(): Int {
        return R.layout.view_repo_list_item
    }

    override fun createView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes(), parent, false)
        view.tag = ViewBinder(view, presenterProvider.get())

        return view
    }

    override fun render(itemView: View, item: RecyclerItem) {
        val viewBinder = itemView.tag as ViewBinder
        viewBinder.bind(item as RepoItem)
    }

    class ViewBinder(private val itemView: View,
                     private val presenter: TrendingReposPresenter) {


        fun bind(repoItem: RepoItem) {
            val repo = repoItem.repo

            itemView.repoName.text = repo.name
            itemView.repoDescription.text = repo.description
            itemView.starCount.text = NumberFormat.getInstance().format(repo.starGazersCount)
            itemView.forkCount.text = NumberFormat.getInstance().format(repo.forksCount)
            itemView.setOnClickListener { presenter.onRepoClicked(repo) }
        }
    }
}
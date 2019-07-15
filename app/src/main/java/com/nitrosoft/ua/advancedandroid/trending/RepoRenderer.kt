package com.nitrosoft.ua.advancedandroid.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.models.RepoItem
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import javax.inject.Inject

class RepoRenderer @Inject constructor() : ItemRenderer<RepoItem> {

    override fun layoutRes(): Int {
        return R.layout.view_repo_list_item
    }

    override fun createView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes(), parent, false)
        view.tag = ViewBinder(view)

        return view
    }

    override fun render(itemView: View, item: RecyclerItem) {
        val viewBinder = itemView.tag as ViewBinder
        viewBinder.bind(item as RepoItem)
    }

    class ViewBinder(private val view: View) {

        fun bind(item: RepoItem) {

        }
    }
}
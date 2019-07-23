package com.nitrosoft.ua.advancedandroid.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import kotlinx.android.synthetic.main.view_user_list_item.view.*
import javax.inject.Inject

class ContributorRenderer @Inject constructor() : ItemRenderer<ContributorListItem> {

    override fun layoutRes(): Int {
        return R.layout.view_user_list_item
    }

    override fun createView(parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes(), parent, false)
        view.tag = ViewBinder(view)

        return view
    }

    override fun render(itemView: View, item: RecyclerItem) {
        val viewBinder = itemView.tag as ViewBinder
        viewBinder.bind(item as ContributorListItem)
    }

    class ViewBinder(private val itemView: View) {


        fun bind(contributorListItem: ContributorListItem) {
            itemView.userNameTv.text = contributorListItem.contributor.login

            Glide.with(itemView.context)
                    .load(contributorListItem.contributor.avatarUrl)
                    .into(itemView.avatarIv)

        }
    }
}
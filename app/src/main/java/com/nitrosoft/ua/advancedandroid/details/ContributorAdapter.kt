package com.nitrosoft.ua.advancedandroid.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.models.Contributor
import kotlinx.android.synthetic.main.view_user_list_item.view.*

class ContributorAdapter : RecyclerView.Adapter<ContributorAdapter.ContributorViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private val data: MutableList<Contributor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_user_list_item, parent, false)

        return ContributorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(contributors: List<Contributor>?) {
        if (contributors == null) {
            data.clear()
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(ContributorDiffCallback(data, contributors))
            data.clear()
            data.addAll(contributors)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class ContributorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(contributor: Contributor) {
            itemView.userNameTv.text = contributor.login
            Glide.with(itemView.avatarIv.context)
                    .load(contributor.avatarUrl)
                    .into(itemView.avatarIv)
        }
    }
}
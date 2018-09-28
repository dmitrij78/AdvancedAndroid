package com.nitrosoft.ua.advancedandroid.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.models.Repo
import kotlinx.android.synthetic.main.view_repo_list_item.view.*
import java.text.NumberFormat

class RepoAdapter(private val repoClickListener: RepoClickListener) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private var data: MutableList<Repo> = mutableListOf()

    init {
        setHasStableIds(true)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_repo_list_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = data[position]

        holder.itemView.setOnClickListener {
            repoClickListener.onRepoClicked(repo)
        }

        holder.itemView.repoName.text = repo.name
        holder.itemView.repoDescription.text = repo.description
        holder.itemView.starCount.text = NumberFormat.getInstance().format(repo.starGazersCount)
        holder.itemView.forkCount.text = NumberFormat.getInstance().format(repo.forksCount)
    }

    fun setData(repos: MutableList<Repo>?) {
        if (repos == null) {
            this.data.clear()
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(RepoDiffCallback(repos, this.data))
            data.clear()
            data.addAll(repos)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    inner class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

interface RepoClickListener {

    fun onRepoClicked(repo: Repo)
}

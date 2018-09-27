package com.nitrosoft.ua.advancedandroid.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.models.Repo

class RepoAdapter(private val data:List<Repo>): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_repo_list_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
    }


    inner class RepoViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        //val tvAnimalType = view.tv_animal_type
    }

}

package com.nitrosoft.ua.poweradapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdpater(private val dataSource: RecyclerDataSource) : RecyclerView.Adapter<RecyclerViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(dataSource.rendererForType(viewType), parent)
    }

    override fun getItemCount(): Int {
        return dataSource.count
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(dataSource.getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return dataSource.viewResourceForPosition(position)!!
    }

    override fun getItemId(position: Int): Long {
        return dataSource.getItem(position).getId()
    }
}
package com.nitrosoft.ua.poweradapter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem

class RecyclerViewHolder(
    private val renderer: ItemRenderer<out RecyclerItem>?,
    parent: ViewGroup
) : RecyclerView.ViewHolder(renderer?.createView(parent)!!) {

    fun bind(item: RecyclerItem) {
        renderer?.render(itemView, item)
    }
}
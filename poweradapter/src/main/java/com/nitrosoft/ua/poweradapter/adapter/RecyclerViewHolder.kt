package com.nitrosoft.ua.poweradapter.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem

class RecyclerViewHolder(private val renderer: ItemRenderer<RecyclerItem>?,
                         itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: RecyclerItem) {
        renderer?.render(itemView, item)
    }
}
package com.nitrosoft.ua.poweradapter.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nitrosoft.ua.poweradapter.item.RecyclerItem

class RecyclerDiffCallback(private val oldList: List<RecyclerItem>,
                           private val newList: List<RecyclerItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].getId() == newList[newItemPosition].getId()
    }
}
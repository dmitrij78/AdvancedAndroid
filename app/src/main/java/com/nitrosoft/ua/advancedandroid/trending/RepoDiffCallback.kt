package com.nitrosoft.ua.advancedandroid.trending

import androidx.recyclerview.widget.DiffUtil
import com.nitrosoft.ua.advancedandroid.models.Repo

class RepoDiffCallback(private val oldList: List<Repo>, private val newList: List<Repo>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
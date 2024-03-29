package com.nitrosoft.ua.advancedandroid.details

import androidx.recyclerview.widget.DiffUtil
import com.nitrosoft.ua.advancedandroid.models.Contributor

class ContributorDiffCallback(
        private val oldList: List<Contributor>,
        private val newList: List<Contributor>) : DiffUtil.Callback() {

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
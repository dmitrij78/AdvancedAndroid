package com.nitrosoft.ua.poweradapter.item

import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.annotation.NonNull

interface ItemRenderer<T : RecyclerItem> {

    @LayoutRes
    fun layoutRes(): Int

    fun createView(parent: ViewGroup): View

    fun render(@NonNull itemView: View, @NonNull item: RecyclerItem)
}

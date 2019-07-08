package com.nitrosoft.ua.poweradapter.adapter

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

class RecyclerDataSource(private val renderers: Map<String, ItemRenderer<out RecyclerItem>>) {

    @SuppressLint("UseSparseArrays")
    private val viewTypeToRendererKeyMap = HashMap<Int, String>()
    private val data: MutableList<RecyclerItem> = ArrayList()

    private var adapterReference = WeakReference<RecyclerView.Adapter<*>>(null)

    internal val count: Int
        get() = data.size

    init {
        for ((key, value) in renderers) {
            viewTypeToRendererKeyMap[value.layoutRes()] = key
        }
    }

    fun setData(newData: List<RecyclerItem>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(RecyclerDiffCallback(data, newData))
        data.clear()
        data.addAll(newData)
        val adapter = adapterReference.get()
        adapter?.let { diffResult.dispatchUpdatesTo(it) }
    }

    internal fun rendererForType(viewType: Int): ItemRenderer<out RecyclerItem>? {
        return renderers[viewTypeToRendererKeyMap[viewType]]
    }

    @LayoutRes
    internal fun viewResourceForPosition(position: Int): Int? {
        return renderers.get(data[position].renderKey())?.layoutRes()
    }

    internal fun getItem(position: Int): RecyclerItem {
        return data[position]
    }

    internal fun attachAdapter(adapter: RecyclerView.Adapter<*>) {
        adapterReference = WeakReference(adapter)
    }

}

package com.nitrosoft.ua.poweradapter.adapter

import android.view.View
import android.view.ViewGroup
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import org.junit.After
import org.junit.Before
import org.junit.Test

class RecyclerDataSourceTest {

    private val itemRenderer1: ItemRenderer<out RecyclerItem> = TesetRenderer(1)
    private val itemRenderer2: ItemRenderer<out RecyclerItem> = TesetRenderer(2)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getCount() {
    }

    @Test
    fun `rendererForType$poweradapter`() {
    }

    @Test
    fun `viewResourceForPosition$poweradapter`() {
    }

    class TestItem(private val id: Long,
                   private val renderKey: String) : RecyclerItem {

        override fun getId(): Long {
            return id
        }

        override fun renderKey(): String {
            return renderKey
        }
    }

    class TesetRenderer(private val layoutRes: Int) : ItemRenderer<TestItem> {
        override fun layoutRes(): Int {
            return layoutRes
        }

        override fun createView(parent: ViewGroup): View {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun render(itemView: View, item: TestItem) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
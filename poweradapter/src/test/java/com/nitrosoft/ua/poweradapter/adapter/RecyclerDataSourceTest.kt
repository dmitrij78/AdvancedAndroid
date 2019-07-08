package com.nitrosoft.ua.poweradapter.adapter

import android.view.View
import android.view.ViewGroup
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RecyclerDataSourceTest {

    private val rendererOne: ItemRenderer<out RecyclerItem> = TestRenderer(1)
    private val rendererTwo: ItemRenderer<out RecyclerItem> = TestRenderer(2)
    private val testItem1: TestItem = TestItem(1, "r1")
    private val testItem2: TestItem = TestItem(2, "r1")
    private val testItem3: TestItem = TestItem(3, "r2")

    private lateinit var dataSource: RecyclerDataSource

    @Before
    fun setUp() {
        val items: List<RecyclerItem> = arrayListOf(testItem1, testItem2, testItem3)
        val renderers: Map<String, ItemRenderer<out RecyclerItem>> =
                hashMapOf("r1" to rendererOne, "r2" to rendererTwo)
        dataSource = RecyclerDataSource(renderers)
        dataSource.seedData(items)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getCount() {
        Assert.assertEquals(3, dataSource.count)
    }

    @Test
    fun getItem() {
        Assert.assertEquals(testItem1, dataSource.getItem(0))
        Assert.assertEquals(testItem2, dataSource.getItem(1))
        Assert.assertEquals(testItem3, dataSource.getItem(2))
    }


    @Test
    fun rendererForType() {
        Assert.assertEquals(rendererOne, dataSource.rendererForType(rendererOne.layoutRes()))
        Assert.assertEquals(rendererTwo, dataSource.rendererForType(rendererTwo.layoutRes()))
    }

    @Test
    fun viewResourceForPosition() {
        Assert.assertEquals(rendererOne.layoutRes(), 1)
        Assert.assertEquals(rendererTwo.layoutRes(), 2)
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

    class TestRenderer(private val layoutRes: Int) : ItemRenderer<TestItem> {
        override fun layoutRes(): Int {
            return layoutRes
        }

        override fun createView(parent: ViewGroup): View {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun render(itemView: View, item: RecyclerItem) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
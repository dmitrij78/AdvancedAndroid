package com.nitrosoft.ua.advancedandroid.details

import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import com.nitrosoft.ua.advancedandroid.models.ContributorRenderer
import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import com.nitrosoft.ua.poweradapter.item.RenderKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet

@Module
abstract class DetailsScreenModule {

    @Binds
    @IntoSet
    abstract fun bindUiManager(uiManager: DetailsUIManger): ScreenLifecycleTask

    @Binds
    @IntoMap
    @RenderKey("CONTRIBUTOR")
    abstract fun bindContributorRenderer(repoRenderer: ContributorRenderer): ItemRenderer<out RecyclerItem>

    @Module
    companion object {

        @Provides
        @ScreenScope
        @JvmStatic
        @JvmSuppressWildcards
        fun provideRecyclerDataSource(renderers: Map<String, ItemRenderer<out RecyclerItem>>): RecyclerDataSource {
            return RecyclerDataSource(renderers)
        }
    }
}
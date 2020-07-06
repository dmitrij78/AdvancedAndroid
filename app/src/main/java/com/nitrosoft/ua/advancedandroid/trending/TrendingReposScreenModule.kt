package com.nitrosoft.ua.advancedandroid.trending

import com.nitrosoft.ua.poweradapter.adapter.RecyclerDataSource
import com.nitrosoft.ua.poweradapter.item.ItemRenderer
import com.nitrosoft.ua.poweradapter.item.RecyclerItem
import com.nitrosoft.ua.poweradapter.item.RenderKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(FragmentComponent::class)
abstract class TrendingReposScreenModule {

    /*@Binds
    @IntoSet
    abstract fun bindUiManager(trendingRepoUIManger: TrendingRepoUIManger): ScreenLifecycleTask*/

    @Binds
    @IntoMap
    @RenderKey("REPO")
    abstract fun bindRepoRenderer(repoRenderer: RepoRenderer): ItemRenderer<out RecyclerItem>

    companion object {

        @Provides
        @JvmSuppressWildcards
        fun provideRecyclerDataSource(renderers: Map<String, ItemRenderer<out RecyclerItem>>): RecyclerDataSource {
            return RecyclerDataSource(renderers)
        }
    }
}

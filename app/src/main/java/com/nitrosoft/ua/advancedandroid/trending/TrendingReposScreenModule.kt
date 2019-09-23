package com.nitrosoft.ua.advancedandroid.trending

import androidx.lifecycle.ViewModel
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import com.nitrosoft.ua.advancedandroid.view_model.ViewModelKey
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
@SuppressWarnings("unused")
abstract class TrendingReposScreenModule {

    @Binds
    @IntoSet
    abstract fun bindUiManager(trendingRepoUIManger: TrendingRepoUIManger): ScreenLifecycleTask

    @Binds
    @IntoMap
    @RenderKey("REPO")
    abstract fun bindRepoRenderer(repoRenderer: RepoRenderer): ItemRenderer<out RecyclerItem>

    @Binds
    @IntoMap
    @ViewModelKey(TrendingRepoViewModel2::class)
    abstract fun bindViewModel2(viewModel2: TrendingRepoViewModel2): ViewModel

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
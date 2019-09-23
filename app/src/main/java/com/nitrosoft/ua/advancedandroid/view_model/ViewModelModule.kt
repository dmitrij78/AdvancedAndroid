package com.nitrosoft.ua.advancedandroid.view_model

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import javax.inject.Provider

@Module
abstract class ViewModelModule {

    @Multibinds
    internal abstract fun multiViewModels(): Map<Class<out ViewModel>, ViewModel>

    @Module
    companion object {

        @Provides
        @JvmSuppressWildcards
        @JvmStatic
        fun provideViewModelFactory(viewModels: Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelFactory {
            return ViewModelFactory(viewModels)
        }
    }
}

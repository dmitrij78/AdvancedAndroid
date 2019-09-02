package com.nitrosoft.ua.advancedandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class ViewModelFactory constructor(private val viewModels: Map<Class<out ViewModel>, Provider<ViewModel>>) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            @Suppress("UNCHECKED_CAST")
            return viewModels[modelClass]?.get() as T
        } catch (e: Exception) {
            throw RuntimeException("Error creating view model for class: ${modelClass.simpleName}", e)
        }
    }
}
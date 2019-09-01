package com.nitrosoft.ua.advancedandroid.trending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nitrosoft.ua.advancedandroid.models.Repo
import javax.inject.Inject

class TrendingRepoViewModel2 @Inject constructor() : ViewModel() {

    private val repos = MutableLiveData<List<Repo>>()

    init {
        loadRepos()
    }

    private fun loadRepos() {
    }
}
package com.nitrosoft.ua.advancedandroid.database.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.engine.Resource
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.database.Converter
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryService @Inject constructor(
        private val appDatabase: AppDatabase,
        private val mapper: Converter<RepoEntity, Repo>) {

    private val repos: LiveData<List<RepoListItem>> = MutableLiveData()

    fun getRepos(): LiveData<Resource<List<Repo>>> {
        return MutableLiveData()
    }
}
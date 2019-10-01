package com.nitrosoft.ua.advancedandroid.database.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.database.Mapper
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.models.RepoListItem
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryService @Inject constructor(
        private val appDatabase: AppDatabase,
        private val mapper: Mapper<Repository, Repo>) {

    private val repos: LiveData<List<RepoListItem>> = MutableLiveData()
/*
    init {
        appDatabase.repositoriesDao().getRepositories()
                .subscribeOn(Schedulers.io())
                .flatMapIterable { it -> it }
                .map { mapper.mapFromEntity(it) }
                .subscribe(
                        favoriteContributorsRelay,
                        Consumer { Timber.e(it, "Error loading favorite contributors from database") }
                )
    }

    fun favoriteContributorIds(): Observable<Set<Long>> {
        return favoriteContributorsRelay
    }

    fun toggleFavorite(contributor: Contributor) {
        runDbOp(Action {
            if (favoriteContributorsRelay.value?.contains(contributor.id)!!) {
                removeFavoriteContributor(contributor)
            } else {
                addFavoriteContributor(contributor)
            }
        })
    }

    private fun addFavoriteContributor(contributor: Contributor) {
        appDatabase.favoriteContributorDao().addFavorite(FavouriteContributor(contributor.id))
    }*/

    private fun runDbOp(action: Action) {
        Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .subscribe({}, { Timber.e(it, "Error performing database operation") })
    }
}
package com.nitrosoft.ua.advancedandroid.database.favorites

import android.annotation.SuppressLint
import com.jakewharton.rxrelay2.BehaviorRelay
import com.nitrosoft.ua.advancedandroid.database.AppDatabase
import com.nitrosoft.ua.advancedandroid.models.Contributor
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SuppressLint("CheckResult")
class FavoriteService @Inject constructor(private val appDatabase: AppDatabase) {

    private val favoriteContributorsRelay: BehaviorRelay<Set<Long>> = BehaviorRelay.create()

    init {
        appDatabase.favoriteContributorDao().getFavoriteContributors()
                .subscribeOn(Schedulers.io())
                .map(Function<List<FavouriteContributor>, Set<Long>> { favoriteContributors ->
                    val contributorIds = HashSet<Long>()
                    for (favouriteContributor in favoriteContributors) {
                        contributorIds.add(favouriteContributor.id)
                    }

                    return@Function contributorIds
                })
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
    }

    private fun removeFavoriteContributor(contributor: Contributor) {
        appDatabase.favoriteContributorDao().deleteFavorite(FavouriteContributor(contributor.id))
    }

    private fun runDbOp(action: Action) {
        Completable.fromAction(action)
                .subscribeOn(Schedulers.io())
                .subscribe({}, { Timber.e(it, "Error performing database operation") })
    }
}
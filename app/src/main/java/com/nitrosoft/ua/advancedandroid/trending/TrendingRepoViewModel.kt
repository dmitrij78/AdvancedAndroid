package com.nitrosoft.ua.advancedandroid.trending

import com.jakewharton.rxrelay2.BehaviorRelay
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Observable
import timber.log.Timber
import java.util.function.Consumer
import javax.inject.Inject

@ScreenScope
class TrendingRepoViewModel @Inject constructor() {

    private val reposRelay: BehaviorRelay<List<Repo>> = BehaviorRelay.create()
    private val errorRelay: BehaviorRelay<Int> = BehaviorRelay.create()
    private val loadRelay: BehaviorRelay<Boolean> = BehaviorRelay.create()

    fun loading(): Observable<Boolean> {
        return loadRelay
    }

    fun repos(): Observable<List<Repo>> {
        return reposRelay
    }

    fun error(): Observable<Int> {
        return errorRelay
    }

    fun loadingUpdated(): BehaviorRelay<Boolean> {
        return loadRelay
    }

    fun requestUpdated(): BehaviorRelay<List<Repo>> {
        return reposRelay
    }

    fun onError(throwable: Throwable): Consumer<Throwable> {
        return Consumer {
            Timber.e(throwable)
        }
    }
}
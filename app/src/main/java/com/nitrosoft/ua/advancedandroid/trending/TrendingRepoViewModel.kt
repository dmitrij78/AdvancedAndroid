package com.nitrosoft.ua.advancedandroid.trending

import com.jakewharton.rxrelay2.BehaviorRelay
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.models.Repo
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import timber.log.Timber
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

    fun loadingUpdated(): Consumer<Boolean> {
        return loadRelay
    }

    fun requestUpdated(): Consumer<List<Repo>> {
        return reposRelay
    }

    fun onError(): Consumer<Throwable> {
        return Consumer {
            Timber.e(it)
            errorRelay.accept(R.string.api_error_repos)
        }
    }
}
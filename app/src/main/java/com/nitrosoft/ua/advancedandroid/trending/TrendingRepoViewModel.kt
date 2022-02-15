package com.nitrosoft.ua.advancedandroid.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxrelay2.BehaviorRelay
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject

@ScreenScope
class TrendingRepoViewModel @Inject constructor()  : ViewModel() {

    private val errorRelay: BehaviorRelay<Int> = BehaviorRelay.create()
    private val loadRelay: BehaviorRelay<Boolean> = BehaviorRelay.create()

    fun loading(): Observable<Boolean> {
        return loadRelay
    }

    fun error(): Observable<Int> {
        return errorRelay
    }

    fun loadingUpdated(): Consumer<Boolean> {
        return loadRelay
    }

    fun reposUpdated(): Action {
        return Action { errorRelay.accept(-1) }
    }

    fun onError(): Consumer<Throwable> {
        return Consumer {
            errorRelay.accept(R.string.api_error_repos)
        }
    }

    class Factory constructor(private val  newsId: String) : ViewModelProvider.Factory{

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == TrendingRepoViewModel::class)
            return  TrendingRepoViewModel() as T
        }

        class Factory {
            fun create(newsId:String) : TrendingRepoViewModel.Factory{
                return Factory(newsId)
            }
        }

    }
}
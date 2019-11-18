package com.nitrosoft.ua.advancedandroid.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.data.ResultWrapper
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.models.Repo
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import timber.log.Timber
import javax.inject.Inject

@ScreenScope
class TrendingRepoViewModel @Inject constructor(
        private val repoRepository: RepoRepository,
        private val screenNavigator: ScreenNavigator
) : ViewModel() {
    val repoList: LiveData<ResultWrapper<List<Repo>>> by lazy {
        return@lazy repoRepository.getTrendingReposCoroutine()
    }

    companion object {
        val TAG: String = "AdvancedAndroidApp." + TrendingRepoViewModel::class.java.simpleName
    }

    override fun onCleared() {
        Timber.tag(TAG).d("onCleared")
    }

    fun onRepoClicked(repo: Repo) {
        screenNavigator.goToRepoDetails(repo.user.login, repo.name)
    }
}
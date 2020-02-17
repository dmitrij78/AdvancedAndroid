package com.nitrosoft.ua.advancedandroid.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nitrosoft.ua.advancedandroid.data.RepoState
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntity
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.repository.RepoRepository
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import timber.log.Timber
import javax.inject.Inject

@ScreenScope
class TrendingRepoViewModel @Inject constructor(
        private val repoRepository: RepoRepository,
        private val screenNavigator: ScreenNavigator
) : ViewModel() {
    val repoList: LiveData<RepoState<List<RepoEntity>>> by lazy {
        return@lazy repoRepository.getTrendingRepos().asLiveData()
    }

    companion object {
        val TAG: String = "AdvancedAndroidApp." + TrendingRepoViewModel::class.java.simpleName
    }

    override fun onCleared() {
        Timber.tag(TAG).d("onCleared")
    }

    fun onRepoClicked(repo: RepoEntity) {
        screenNavigator.goToRepoDetails(repo.userLogin, repo.name)
    }
}
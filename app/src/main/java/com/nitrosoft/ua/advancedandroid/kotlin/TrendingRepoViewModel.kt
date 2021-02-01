package com.nitrosoft.ua.advancedandroid.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nitrosoft.ua.advancedandroid.base.createTag
import com.nitrosoft.ua.advancedandroid.data.RepoState
import com.nitrosoft.ua.advancedandroid.database.repos.RepoEntity
import com.nitrosoft.ua.advancedandroid.repository.RepoRepository
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class TrendingRepoViewModel @Inject constructor(
        private val repoRepository: RepoRepository,
        private val screenNavigator: ScreenNavigator
) : ViewModel() {

    companion object {
        val TAG: String = createTag(TrendingRepoViewModel::class.java.simpleName)
    }

    private val _repoList = MutableStateFlow(emptyList<RepoEntity>())

    val repoList2: StateFlow<List<RepoEntity>> = _repoList

    init {
        Timber.d("")


    }


    val repoList: LiveData<RepoState<List<RepoEntity>>> by lazy {
        return@lazy repoRepository.getTrendingRepos().asLiveData()
    }

    override fun onCleared() {
        Timber.tag(TAG).d("onCleared")
    }

    fun onRepoClicked(repo: RepoEntity) {
        screenNavigator.goToRepoDetails(repo.userLogin, repo.name)
    }
}
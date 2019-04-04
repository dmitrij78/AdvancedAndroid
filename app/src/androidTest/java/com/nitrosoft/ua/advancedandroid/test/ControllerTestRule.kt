package com.nitrosoft.ua.advancedandroid.test

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.nitrosoft.ua.advancedandroid.base.TestApplication
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.data.TestRepoService
import com.nitrosoft.ua.advancedandroid.ui.TestScreenNavigator

class ControllerTestRule<T : Activity>(activityClass: Class<T>) :
        ActivityTestRule<T>(activityClass, true, false) {

    val screenNavigator: TestScreenNavigator = TestApplication.getComponent().screenNavigator()
    val repoService: TestRepoService = TestApplication.getComponent().testRepoService()
    val repoRepository: RepoRepository = TestApplication.getComponent().repoRepository()

    fun clearTest() {
        repoService.clearErrorFlags()
        repoService.clearHoldFlags()
        repoRepository.clearCache()
    }
}

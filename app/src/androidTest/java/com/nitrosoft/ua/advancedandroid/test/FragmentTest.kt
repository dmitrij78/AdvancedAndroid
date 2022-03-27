package com.nitrosoft.ua.advancedandroid.test

import android.content.Intent
import androidx.fragment.app.Fragment
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.data.TestRepoService
import com.nitrosoft.ua.advancedandroid.activity.MainActivity
import com.nitrosoft.ua.advancedandroid.ui.TestScreenNavigator
import org.junit.Rule

abstract class FragmentTest {

    @JvmField
    @Rule val testRule: ControllerTestRule<MainActivity> = ControllerTestRule(MainActivity::class.java)

    protected var repoService: TestRepoService
    private var repoRepository: RepoRepository
    private var screenNavigator: TestScreenNavigator

    private val controllerToLaunch: Fragment by lazy {
        controllerToLaunch()
    }

    init {
        repoService = testRule.repoService
        repoRepository = testRule.repoRepository
        screenNavigator = testRule.screenNavigator

        screenNavigator.overrideInitialFragment(controllerToLaunch)
    }

    protected fun launch() {
        launch(null)
    }

    private fun launch(intent: Intent?) {
        testRule.launchActivity(intent)
    }

    protected abstract fun controllerToLaunch(): Fragment
}

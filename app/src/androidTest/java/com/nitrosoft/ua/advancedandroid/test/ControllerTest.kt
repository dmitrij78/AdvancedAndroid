package com.nitrosoft.ua.advancedandroid.test

import android.content.Intent
import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.data.RepoRepository
import com.nitrosoft.ua.advancedandroid.data.TestRepoService
import com.nitrosoft.ua.advancedandroid.home.MainActivity
import com.nitrosoft.ua.advancedandroid.ui.TestScreenNavigator
import org.junit.Rule

abstract class ControllerTest {

    @JvmField
    @Rule val testRule: ControllerTestRule<MainActivity> = ControllerTestRule(MainActivity::class.java)

    protected var repoService: TestRepoService
    private var repoRepository: RepoRepository
    private var screenNavigator: TestScreenNavigator

    init {
        repoService = testRule.repoService
        repoRepository = testRule.repoRepository
        screenNavigator = testRule.screenNavigator

        screenNavigator.overrideInitialController(controllerToLaunch())
    }

    protected fun launch() {
        launch(null)
    }

    private fun launch(intent: Intent?) {
        testRule.launchActivity(intent)
    }

    protected abstract fun controllerToLaunch(): Controller
}

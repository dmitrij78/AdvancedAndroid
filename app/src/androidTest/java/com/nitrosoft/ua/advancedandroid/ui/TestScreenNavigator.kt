package com.nitrosoft.ua.advancedandroid.ui

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestScreenNavigator @Inject constructor(private val defaultScreenNavigator: DefaultScreenNavigator) : ScreenNavigator {

    private var overrideController: Controller? = null

    fun overrideInitialController(controller: Controller) {
        this.overrideController = controller
    }

    override fun initWithRouter(router: Router, rootScreen: Controller) {
        val controller: Controller = overrideController ?: rootScreen
        defaultScreenNavigator.initWithRouter(router, controller)
    }

    override fun pop(): Boolean {
        return defaultScreenNavigator.pop()
    }

    override fun goToRepoDetails(repoOwner: String, repoName: String) {
        defaultScreenNavigator.goToRepoDetails(repoOwner, repoName)
    }

    override fun clear() {
        defaultScreenNavigator.clear()
    }
}
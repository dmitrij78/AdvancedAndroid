package com.nitrosoft.ua.advancedandroid.ui

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestScreenNavigator @Inject constructor(defaultScreenNavigator: DefaultScreenNavigator) : ScreenNavigator {

    private var overrideController: Controller? = null

    fun overrideInitialController(controller: Controller) {
        this.overrideController = controller
    }

    override fun initWithRouter(router: Router, rootScreen: Controller) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pop(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun goToRepoDetails(repoOwner: String, repoName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.nitrosoft.ua.advancedandroid.ui

import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import javax.inject.Singleton

@Singleton
class TestScreenNavigator : ActivityLifecycleTask(), ScreenNavigator {

    private var defaultScreenNavigator: DefaultScreenNavigator = DefaultScreenNavigator()
    private var overrideController: Controller? = null

    override fun onCreate(appCompatActivity: AppCompatActivity) {
        if (appCompatActivity !is RouterProvider) {
            throw IllegalArgumentException("Activity must be instance of RouterProvider")
        }

        val routerProvider = appCompatActivity as RouterProvider
        val launchController: Controller = overrideController ?: routerProvider.initialScreen()
        defaultScreenNavigator.initWithRouter(routerProvider.getRouter(), launchController)
    }

    override fun onDestroy(appCompatActivity: AppCompatActivity) {
        defaultScreenNavigator.onDestroy(appCompatActivity)
    }

    override fun pop(): Boolean {
        return defaultScreenNavigator.pop()
    }

    override fun goToRepoDetails(repoOwner: String, repoName: String) {
        defaultScreenNavigator.goToRepoDetails(repoOwner, repoName)
    }

    fun overrideInitialController(controller: Controller) {
        this.overrideController = controller
    }
}
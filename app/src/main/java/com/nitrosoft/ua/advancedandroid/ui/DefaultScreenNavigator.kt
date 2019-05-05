package com.nitrosoft.ua.advancedandroid.ui

import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.nitrosoft.ua.advancedandroid.details.RepoDetailsController
import com.nitrosoft.ua.advancedandroid.di.ActivityScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import javax.inject.Inject

@ActivityScope
class DefaultScreenNavigator @Inject constructor() : ActivityLifecycleTask(), ScreenNavigator {

    private var router: Router? = null

    override fun onCreate(appCompatActivity: AppCompatActivity) {
        if (appCompatActivity !is RouterProvider) {
            throw IllegalArgumentException("Activity must be instance of RouterProvider")
        }

        val routerProvider = appCompatActivity as RouterProvider
        initWithRouter(routerProvider.getRouter(), routerProvider.initialScreen())
    }

    override fun onDestroy(appCompatActivity: AppCompatActivity) {
        router = null
    }

    override fun pop(): Boolean {
        return router != null && router!!.handleBack()
    }

    override fun goToRepoDetails(repoOwner: String, repoName: String) {
        router?.pushController(RouterTransaction.with(RepoDetailsController.newInstance(repoName, repoOwner))
                .pushChangeHandler(FadeChangeHandler())
                .popChangeHandler(FadeChangeHandler())
        )
    }

    fun initWithRouter(router: Router, rootScreen: Controller) {
        this.router = router
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(rootScreen))
        }
    }
}
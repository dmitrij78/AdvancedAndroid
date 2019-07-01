package com.nitrosoft.ua.advancedandroid.base

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.nitrosoft.ua.advancedandroid.di.Injector
import com.nitrosoft.ua.advancedandroid.di.ScreenInjector
import com.nitrosoft.ua.advancedandroid.lifecycle.ActivityLifecycleTask
import com.nitrosoft.ua.advancedandroid.ui.ActivityViewInterceptor
import com.nitrosoft.ua.advancedandroid.ui.RouterProvider
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), RouterProvider {

    companion object {
        const val INSTANCE_ID = "instance_id"
    }

    @Inject lateinit var screenInjector: ScreenInjector
    @Inject lateinit var screenNavigator: ScreenNavigator
    @Inject lateinit var activityViewInterceptor: ActivityViewInterceptor
    @Inject lateinit var activityLifecycleTasks: Set<@JvmSuppressWildcards ActivityLifecycleTask>

    @Suppress("unused")
    @Inject
    lateinit var appContext: Context

    private lateinit var router: Router
    private lateinit var instanceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        instanceId = when (savedInstanceState) {
            null -> UUID.randomUUID().toString()
            else -> savedInstanceState.getString(INSTANCE_ID) ?: ""
        }

        Injector.inject(this)

        activityViewInterceptor.setContentView(this, layoutRes())

        router = Conductor.attachRouter(this, screenContainer, savedInstanceState)

        monitorBackStack()

        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onCreate(this)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onStart(this)
        }
    }

    override fun onResume() {
        super.onResume()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onResume(this)
        }
    }

    override fun onPause() {
        super.onPause()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onPause(this)
        }
    }

    override fun onStop() {
        super.onStop()
        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onStop(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing) {
            Injector.clear(this)
        }

        activityViewInterceptor.clear()

        for (activityLifecycleTask in activityLifecycleTasks) {
            activityLifecycleTask.onDestroy(this)
        }
    }

    override fun getRouter(): Router {
        return router
    }

    override fun onBackPressed() {
        if (!screenNavigator.pop()) {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INSTANCE_ID, instanceId)
    }


    fun getInstanceId(): String {
        return instanceId
    }

    abstract fun layoutRes(): Int

    abstract override fun initialScreen(): Controller

    private fun monitorBackStack() {
        router.addChangeListener(object : ControllerChangeHandler.ControllerChangeListener {
            override fun onChangeStarted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup, handler: ControllerChangeHandler) {}

            override fun onChangeCompleted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup, handler: ControllerChangeHandler) {
                if (!isPush && from != null) {
                    Injector.clear(from)
                }
            }
        })
    }
}
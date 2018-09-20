package com.nitrosoft.ua.advancedandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.Injector
import com.nitrosoft.ua.advancedandroid.di.ScreenInjector
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val INSTANCE_ID = "instance_id"
    }

    @Inject
    lateinit var screenInjector: ScreenInjector
    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private lateinit var router: Router

    private lateinit var instanceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        instanceId = when (savedInstanceState) {
            null -> UUID.randomUUID().toString()
            else -> savedInstanceState.getString(INSTANCE_ID) ?: ""
        }

        Injector.inject(this)

        setContentView(layoutRes())

        val screenContainer = findViewById<ViewGroup>(R.id.screen_container)
                ?: throw UnsupportedOperationException("Activity must have a view with id: screen_container")

        router = Conductor.attachRouter(this, screenContainer, savedInstanceState)
        screenNavigator.initWithRouter(router, initialScreen())

        monitorBackStack()

        super.onCreate(savedInstanceState)
    }

    private fun monitorBackStack() {
        router.addChangeListener(object : ControllerChangeHandler.ControllerChangeListener {
            override fun onChangeStarted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup, handler: ControllerChangeHandler) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChangeCompleted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup, handler: ControllerChangeHandler) {
                if (!isPush && from != null) {
                    Injector.clear(from)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        screenNavigator.clear()

        if (isFinishing) {
            Injector.clear(this)
        }
    }

    override fun onBackPressed() {
        if (!screenNavigator.pop()) {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(INSTANCE_ID, instanceId)
    }

    fun getInstanceId(): String {
        return instanceId
    }

    abstract fun layoutRes(): Int

    abstract fun initialScreen(): Controller
}
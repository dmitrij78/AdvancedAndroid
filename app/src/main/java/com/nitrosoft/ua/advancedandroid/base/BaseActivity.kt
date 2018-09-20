package com.nitrosoft.ua.advancedandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nitrosoft.ua.advancedandroid.di.Injector
import java.util.*

abstract class BaseActivity: AppCompatActivity() {

    companion object {
        const val INSTANCE_ID = "instance_id"
    }

    private lateinit var instanceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        instanceId = when(savedInstanceState) {
            null -> UUID.randomUUID().toString()
            else -> savedInstanceState.getString(INSTANCE_ID) ?: ""
        }
        //Injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            //Injector.clear(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(INSTANCE_ID, instanceId)
    }

    fun getInstanceId(): String {
        return instanceId
    }
}
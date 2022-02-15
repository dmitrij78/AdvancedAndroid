package com.nitrosoft.ua.advancedandroid.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.settings.DebugPreferences
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DebugActivityViewInterceptor @Inject constructor(private val debugPreferences: DebugPreferences) :
    ActivityViewInterceptor {

    private lateinit var mockResponsesSwitch: Switch

    private val disposables = CompositeDisposable()

    @SuppressLint("InflateParams")
    override fun setContentView(activity: Activity, activityRoot: View) {
        val debugLayout = LayoutInflater.from(activity).inflate(R.layout.debug_drawer, null)
        mockResponsesSwitch = debugLayout.findViewById(R.id.sw_mock_responses)

        initializePrefs()

        debugLayout.findViewById<ViewGroup>(R.id.activity_layout_container).addView(activityRoot)

        activity.setContentView(debugLayout)
    }

    override fun clear() {
        disposables.clear()
    }

    private fun initializePrefs() {
        mockResponsesSwitch.isChecked = debugPreferences.mockResponseEnabled()

        disposables.addAll(
            RxCompoundButton.checkedChanges(mockResponsesSwitch)
                .subscribe { checked ->
                    debugPreferences.setUseMockResponse(checked)
                }
        )
    }
}
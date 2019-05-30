package com.nitrosoft.ua.advancedandroid.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.settings.DebugPreferences
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DebugActivityViewInterceptor @Inject constructor(private val debugPreferences: DebugPreferences) : ActivityViewInterceptor {

    @BindView(R.id.sw_mock_responses) lateinit var mockResponsesSwitch: Switch

    private val disposables = CompositeDisposable()

    private lateinit var unBinder: Unbinder

    @SuppressLint("InflateParams")
    override fun setContentView(activity: Activity, layoutRes: Int) {
        val debugLayout = LayoutInflater.from(activity).inflate(R.layout.debug_drawer, null)
        unBinder = ButterKnife.bind(this, debugLayout)

        initializePrefs()

        val activityLayout = LayoutInflater.from(activity).inflate(layoutRes, null)
        debugLayout.findViewById<ViewGroup>(R.id.activity_layout_container).addView(activityLayout)

        activity.setContentView(debugLayout)
    }

    override fun clear() {
        disposables.clear()
        unBinder.unbind()
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
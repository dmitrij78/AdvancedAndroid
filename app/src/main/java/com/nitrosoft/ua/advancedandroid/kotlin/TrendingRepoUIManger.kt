package com.nitrosoft.ua.advancedandroid.kotlin

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import javax.inject.Inject

@ScreenScope
class TrendingRepoUIManger @Inject constructor() : ScreenLifecycleTask() {

    override fun onEnterScope(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.screen_title_trending)
    }

    override fun onExitScope() {
    }
}
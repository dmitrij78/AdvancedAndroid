package com.nitrosoft.ua.advancedandroid.trending

import android.view.View
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import javax.inject.Inject

@ScreenScope
class TrendingRepoUIManger @Inject constructor() :
    ScreenLifecycleTask() {

    override fun onEnterScope(view: View) {
        //view.toolbar.setTitle(R.string.screen_title_trending)
    }

    override fun onExitScope() {
    }
}
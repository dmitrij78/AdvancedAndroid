package com.nitrosoft.ua.advancedandroid.trending

import android.view.View
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import com.nitrosoft.ua.advancedandroid.util.ButterKnifeUtils
import javax.inject.Inject

@ScreenScope
class TrendingRepoUIManger @Inject constructor(private val butterKnifeUtils: ButterKnifeUtils) :
        ScreenLifecycleTask() {

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    private lateinit var unBinder: Unbinder

    override fun onEnterScope(view: View) {
        unBinder = ButterKnife.bind(this, view)
        toolbar.setTitle(R.string.screen_title_trending)
    }

    override fun onExitScope() {
        butterKnifeUtils.unbind(unBinder)
    }
}
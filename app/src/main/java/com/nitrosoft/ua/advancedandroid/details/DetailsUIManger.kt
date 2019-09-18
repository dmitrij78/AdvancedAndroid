package com.nitrosoft.ua.advancedandroid.details

import android.view.View
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import kotlinx.android.synthetic.main.app_bar.view.*
import javax.inject.Inject
import javax.inject.Named

@ScreenScope
class DetailsUIManger @Inject constructor(
        @Named(RepoDetailsFragment.REPO_NAME_KEY) private val name: String,
        private val screenNavigator: ScreenNavigator) : ScreenLifecycleTask() {

    override fun onEnterScope(view: View) {

        view.toolbar.title = name
        view.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        view.toolbar.setNavigationOnClickListener {
            screenNavigator.pop()
        }
    }

    override fun onExitScope() {
    }
}
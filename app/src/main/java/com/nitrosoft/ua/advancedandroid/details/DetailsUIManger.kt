package com.nitrosoft.ua.advancedandroid.details

import android.view.View
import android.widget.Toolbar
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import javax.inject.Inject
import javax.inject.Named

@ScreenScope
class DetailsUIManger @Inject constructor(
        @Named(RepoDetailsFragment.REPO_NAME_KEY) private val name: String,
        private val screenNavigator: ScreenNavigator) : ScreenLifecycleTask() {

    override fun onEnterScope(view: View) {

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = name
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            screenNavigator.pop()
        }
    }

    override fun onExitScope() {
    }
}
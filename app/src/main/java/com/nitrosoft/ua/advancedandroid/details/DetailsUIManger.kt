package com.nitrosoft.ua.advancedandroid.details

import android.view.View
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.di.ScreenScope
import com.nitrosoft.ua.advancedandroid.lifecycle.ScreenLifecycleTask
import com.nitrosoft.ua.advancedandroid.ui.ScreenNavigator
import com.nitrosoft.ua.advancedandroid.util.ButterKnifeUtils
import javax.inject.Inject
import javax.inject.Named

@ScreenScope
class DetailsUIManger @Inject constructor(
        @Named(RepoDetailsController.REPO_NAME_KEY) private val name: String,
        private val butterKnifeUtils: ButterKnifeUtils,
        private val screenNavigator: ScreenNavigator) : ScreenLifecycleTask() {

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    private lateinit var unBinder: Unbinder

    override fun onEnterScope(view: View) {
        unBinder = ButterKnife.bind(this, view)
        toolbar.title = name
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            screenNavigator.pop()
        }
    }

    override fun onExitScope() {
        butterKnifeUtils.unbind(unBinder)
    }
}
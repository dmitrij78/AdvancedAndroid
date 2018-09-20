package com.nitrosoft.ua.advancedandroid.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseController

class TrendingReposController : BaseController() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.view_trending_repo, container, false)
    }
}
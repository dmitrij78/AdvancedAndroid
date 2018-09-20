package com.nitrosoft.ua.advancedandroid.home

import com.bluelinelabs.conductor.Controller
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseActivity
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposController

class MainActivity : BaseActivity() {

    override fun initialScreen(): Controller {
        return TrendingReposController()
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }
}

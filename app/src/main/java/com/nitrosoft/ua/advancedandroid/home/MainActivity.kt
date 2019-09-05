package com.nitrosoft.ua.advancedandroid.home

import androidx.fragment.app.Fragment
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseActivity
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposFragment

open class MainActivity : BaseActivity() {

    override fun initialFragment(): Fragment {
        return TrendingReposFragment.newInstance()
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }
}

package com.nitrosoft.ua.advancedandroid.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseActivity
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposFragment

open class MainActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
    }

//    override fun initialFragment(): Fragment {
//        return TrendingReposFragment.newInstance()
//    }
//
//    override fun layoutRes(): Int {
//        return R.layout.activity_main
//    }
}

package com.nitrosoft.ua.advancedandroid.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nitrosoft.ua.advancedandroid.R
import com.nitrosoft.ua.advancedandroid.base.BaseActivity
import com.nitrosoft.ua.advancedandroid.databinding.ActivityMainBinding
import com.nitrosoft.ua.advancedandroid.trending.TrendingReposFragment

open class MainActivity : BaseActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun layoutRes(): View = binding.root

    override fun initialFragment(): Fragment = TrendingReposFragment.newInstance()
}

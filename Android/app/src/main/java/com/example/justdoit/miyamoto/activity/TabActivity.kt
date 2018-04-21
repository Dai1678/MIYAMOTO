package com.example.justdoit.miyamoto.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.Unit.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        val tabNames = arrayOf("Pasilitator", "Pasilist")

        val fragmentManager = supportFragmentManager
        val viewPagerAdapter = ViewPagerAdapter(fragmentManager, tabNames)

        pager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(pager)

    }
}

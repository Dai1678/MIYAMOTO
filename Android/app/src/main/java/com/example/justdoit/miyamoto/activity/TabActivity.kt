package com.example.justdoit.miyamoto.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.justdoit.miyamoto.Pasilist.PasilistActivity
import com.example.justdoit.miyamoto.Pasilist.PasilistFragment
import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.R.id.pager
import com.example.justdoit.miyamoto.R.id.tabLayout
import com.example.justdoit.miyamoto.Unit.ViewPagerAdapter
import com.example.justdoit.miyamoto.fragment.MyProfileFragment
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : AppCompatActivity(), MyProfileFragment.OnMyProfileListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        val sharedPreferences = this.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val mode = sharedPreferences.getBoolean("mode", false)

        val matchingV=findViewById<TextView>(R.id.is_matching)

        if(mode){
            matchingV.visibility= View.VISIBLE
        }
        else{
            matchingV.visibility=View.GONE
        }

        val tabNames = arrayOf("Pasilitator", "Pasilist")

        val fragmentManager = supportFragmentManager
        val viewPagerAdapter = ViewPagerAdapter(fragmentManager, tabNames)

        pager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(pager)

    }

    override fun intentWish() {
        val intent = Intent(this, WishListActivity::class.java)
        startActivity(intent)
    }
}

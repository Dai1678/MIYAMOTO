package com.example.justdoit.miyamoto.Unit

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.justdoit.miyamoto.Pasilist.PasilistFragment
import com.example.justdoit.miyamoto.fragment.MyProfileFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, private val tabNames: Array<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> MyProfileFragment.newInstance()
            1 -> PasilistFragment.newInstance()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabNames[position]
    }
}
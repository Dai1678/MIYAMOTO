package com.example.justdoit.miyamoto.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justdoit.miyamoto.Pasilist.PasilistFragment
import com.example.justdoit.miyamoto.R

class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() : MyProfileFragment {
            return MyProfileFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

}
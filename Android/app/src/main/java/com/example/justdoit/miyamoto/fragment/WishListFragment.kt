package com.example.justdoit.miyamoto.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justdoit.miyamoto.R
import kotlinx.android.synthetic.main.fragment_login_form.*
import kotlinx.android.synthetic.main.fragment_wish_list.*


class WishListFragment : Fragment() {

    companion object {
        fun getInstance() : WishListFragment {
            return WishListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createWish.setOnClickListener { view ->

        }
    }

}
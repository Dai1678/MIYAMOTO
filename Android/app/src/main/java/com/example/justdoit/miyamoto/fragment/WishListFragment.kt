package com.example.justdoit.miyamoto.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.justdoit.miyamoto.Pasilist.PasilistAdapter
import com.example.justdoit.miyamoto.Pasilist.PasilistModel
import com.example.justdoit.miyamoto.R
import kotlinx.android.synthetic.main.fragment_login_form.*
import kotlinx.android.synthetic.main.fragment_wish_list.*


class WishListFragment : Fragment() {

    var mWishlistAdapter: PasilistAdapter?=null
    var mWishlist: ListView?=null

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

        mWishlist=view.findViewById(R.id.list_wish)
        mWishlistAdapter= PasilistAdapter(context!!,R.layout.item_pasilist)

        for(i in 0..20){
            val sample= PasilistModel(0,"東京駅","午後2時まで",3000)
            mWishlistAdapter?.add(sample)
        }
        mWishlist?.adapter=mWishlistAdapter
    }

}
package com.example.justdoit.miyamoto.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justdoit.miyamoto.R
import kotlinx.android.synthetic.main.fragment_my_profile.*

class MyProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var listener: OnMyProfileListener

    companion object {
        fun newInstance() : MyProfileFragment {
            return MyProfileFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnMyProfileListener){
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        //WishListFragmentへ
        listener.intentWish()
    }

    interface OnMyProfileListener{
        fun intentWish()
    }

}
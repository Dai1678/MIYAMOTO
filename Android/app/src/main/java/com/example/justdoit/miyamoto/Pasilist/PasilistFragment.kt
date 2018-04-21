package com.example.justdoit.miyamoto.Pasilist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.example.justdoit.miyamoto.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PasilistFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PasilistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasilistFragment : Fragment() {

    var mPasilistAdapter:PasilistAdapter?=null
    var mPasilist:ListView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_pasilist, container, false)

        mPasilist=view.findViewById(R.id.pasilist)
        mPasilistAdapter=PasilistAdapter(context!!,R.layout.item_pasilist)

        for(i in 0..20){
            val sample=PasilistModel(0,"東京駅","午後2時まで",3000)
            mPasilistAdapter?.add(sample)
        }
        mPasilist?.adapter=mPasilistAdapter

        return view
    }

}// Required empty public constructor

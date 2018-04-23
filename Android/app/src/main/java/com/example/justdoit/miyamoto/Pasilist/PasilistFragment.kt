package com.example.justdoit.miyamoto.Pasilist

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.justdoit.miyamoto.ApiClient

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.activity.PaisluActivity
import com.example.justdoit.miyamoto.R.id.swipeListLayout
import com.example.justdoit.miyamoto.activity.TabActivity
import com.example.justdoit.miyamoto.fragment.MainFragment
import kotlinx.android.synthetic.main.fragment_pasilist.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PasilistFragment : Fragment(), AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    var mPasilistAdapter:PasilistAdapter?=null
    var mPasilist:ListView?=null

    var token=""

    private var userId = 0
    private lateinit var location: String
    private lateinit var timeLimit: String
    private var amount = 0

    private var maxPasilistSize = 0

    private lateinit var listener: OnPasilistListener

    companion object {
        fun newInstance() : PasilistFragment {
            return PasilistFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnPasilistListener){
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_pasilist, container, false)

        mPasilist=view.findViewById<ListView?>(R.id.pasilist)
        mPasilistAdapter=PasilistAdapter(context!!,R.layout.item_pasilist)

        val sharedPreferences = this.activity!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", "")

        //TODO Pasilistデータの中身をサーバーからGET確認
        launch(UI) {
            ApiClient.shared.fetchPasiList().await()?.let {
                it.forEach { mPasilistAdapter?.add(it!!) }
                mPasilist?.adapter = mPasilistAdapter
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeListLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW)
        swipeListLayout.setOnRefreshListener(this)
        pasilist.onItemClickListener = this
    }


    override fun onItemClick(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
        //TODO パシリストが注文を受諾したら、他の注文は受諾できないようにLOCKする


        //TODO PasiluActivityへ遷移
        val intent= Intent(context,PaisluActivity::class.java)
        intent.putExtra("token",token)
        val id=mPasilistAdapter?.getItem(position)?.userId
        intent.putExtra("id",id)
        val shoppingListId = mPasilistAdapter?.getItem(position)?.shoppingListId
        intent.putExtra("shoppingListId", shoppingListId)
        startActivity(intent)
    }

    //引っ張ったときの処理(非同期処理など)
    override fun onRefresh() {
        launch(UI) {
            ApiClient.shared.fetchPasiList().await()?.let {
                mPasilistAdapter?.clear()
                it.forEach { mPasilistAdapter?.add(it!!) }
                mPasilist?.adapter = mPasilistAdapter
            }
        }

        if (swipeListLayout.isRefreshing){
            swipeListLayout.isRefreshing = false
        }
    }

    interface OnPasilistListener {
        fun intentPasilu()
    }

}

package com.example.justdoit.miyamoto.Pasilist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.activity.MainActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PasilistFragment : Fragment(), AdapterView.OnItemClickListener {

    var mPasilistAdapter:PasilistAdapter?=null
    var mPasilist:ListView?=null

    private var userId : Int = 0
    private lateinit var timeLimit : String
    private lateinit var address : String

    private var totalAmount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_pasilist, container, false)

        mPasilist=view.findViewById(R.id.pasilist)
        mPasilistAdapter=PasilistAdapter(context!!,R.layout.item_pasilist)

        //TODO Pasilistデータの中身をサーバーからGET


        for(i in 0..20){
            val sample=PasilistModel(userId, address, timeLimit, totalAmount)
            mPasilistAdapter?.add(sample)
        }
        mPasilist?.adapter=mPasilistAdapter

        return view
    }


    override fun onItemClick(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
        //TODO 詳細画面へ遷移

    }

    private fun getPasilistData(maxListSize : Int){
        val request = Request.Builder()
                .url("http://140.82.9.44:3000/match/pasilist")     // 130010->東京
                .get()
                .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun
                    onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                (context as MainActivity).runOnUiThread{
                    val json: JSONObject
                    try {
                        json = JSONObject(res)

                        val array = json.getJSONArray("result") as Array<JSONObject>
                        array.forEach {
                            Log.i("result", it.getString("timeLimit"))
                        }


                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

}// Required empty public constructor

package com.example.justdoit.miyamoto.Pasilist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView

import com.example.justdoit.miyamoto.R
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PasilistFragment : Fragment(), AdapterView.OnItemClickListener {

    var mPasilistAdapter:PasilistAdapter?=null
    var mPasilist:ListView?=null

    private var maxPasilistSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_pasilist, container, false)

        mPasilist=view.findViewById<ListView?>(R.id.pasilist)
        mPasilistAdapter=PasilistAdapter(context!!,R.layout.item_pasilist)

        //TODO Pasilistデータの中身をサーバーからGET
        getPasilistData()

        val pasilistModel = PasilistModel()
        for(i in 0..maxPasilistSize){
            val sample=PasilistModel(pasilistModel.userId,  pasilistModel.location, pasilistModel.timeLimit, pasilistModel.amount)
            mPasilistAdapter?.add(sample)
        }
        mPasilist?.adapter=mPasilistAdapter

        return view
    }


    override fun onItemClick(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
        //TODO 詳細画面へ遷移

    }

    private fun getPasilistData(){
        val request = Request.Builder()
                .url("http://140.82.9.44:3000/match/pasilist")
                .get()
                .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.i("error","error")
            }

            @Throws(IOException::class)
            override fun
                    onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                (context as PasilistActivity).runOnUiThread{
                    val json: JSONObject
                    try {
                        json = JSONObject(res)

                        val resultArray=json.getJSONArray("result")
                        maxPasilistSize = resultArray.length()

                        resultArray?.let{
                            for(i in 0..resultArray.length()) {
                                val resultJson=resultArray[i] as JSONObject
                                val pasilistModel = PasilistModel()
                                pasilistModel.userId =resultJson.getInt("userid")
                                pasilistModel.amount = resultJson.getInt("totalAmount")
                                pasilistModel.location = resultJson.getString("address")
                                pasilistModel.timeLimit = resultJson.getString("timeLimit")
                                Log.i("id",resultJson.getInt("userid").toString())
                            }
                        }


                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

}// Required empty public constructor

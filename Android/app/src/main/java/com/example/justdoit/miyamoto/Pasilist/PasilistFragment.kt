package com.example.justdoit.miyamoto.Pasilist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.activity.TabActivity
import com.example.justdoit.miyamoto.fragment.MainFragment
import kotlinx.android.synthetic.main.fragment_pasilist.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PasilistFragment : Fragment(), AdapterView.OnItemClickListener {

    var mPasilistAdapter:PasilistAdapter?=null
    var mPasilist:ListView?=null

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

        //TODO Pasilistデータの中身をサーバーからGET確認
        getPasilistData()

        val pasilistModel = PasilistModel()
        for(i in 0..maxPasilistSize){
            val sample=PasilistModel(pasilistModel.userId,  pasilistModel.location, pasilistModel.timeLimit, pasilistModel.amount)
            mPasilistAdapter?.add(sample)
        }
        mPasilist?.adapter=mPasilistAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pasilist.onItemClickListener = this
    }


    override fun onItemClick(adapterView: AdapterView<*>?, view: View, position: Int, id: Long) {
        //TODO PasiluActivityへ遷移
        //listener.intentPasilu()
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
                (context as TabActivity).runOnUiThread{
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

    interface OnPasilistListener {
        fun intentPasilu()
    }

}

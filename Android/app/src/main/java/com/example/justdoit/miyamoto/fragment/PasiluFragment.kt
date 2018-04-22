package com.example.justdoit.miyamoto.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.activity.MainActivity
import com.example.justdoit.miyamoto.activity.PaisluActivity
import com.example.justdoit.miyamoto.activity.TabActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PasiluFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PasiluFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasiluFragment : Fragment() {

    var token=""
    var userId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pasilu, container, false)

        token=arguments!!.getString("token")
        userId=arguments!!.getInt("id")

        val pasiluBtn = view.findViewById<Button>(R.id.pasilu)

        pasiluBtn.setOnClickListener {

            // todo ここでpostするデータ付与して
            val formBody = FormBody.Builder()
                    .add("token", token)
                    .add("id", userId.toString())
                    .build()

            val url="http://140.82.9.44:3000/match/acceptRequest"
            val request = Request.Builder()
                    .url(url)       // HTTPアクセス POST送信 テスト確認用ページ
                    .post(formBody)
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val res = response.body()?.string()
                    (context as MainActivity).runOnUiThread {
                        val json: JSONObject
                        try {
                            json = JSONObject(res)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            })
        }

        return view
    }

    private fun fetchShoppingList(shoppingListId: Int) {
        val request = Request.Builder()
                .url("http://140.82.9.44:3000/match/shoppingList/$shoppingListId?token=$token")
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
                (context as PaisluActivity).runOnUiThread{
                    val json: JSONObject
                    try {
                        json = JSONObject(res)
                        json.getJSONArray("result")?.let {
                            val maxCount = it.length()
                            for (i in 0..maxCount) {
                                it[i] as JSONObject
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

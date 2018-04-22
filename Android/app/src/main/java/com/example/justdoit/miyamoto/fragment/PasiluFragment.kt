package com.example.justdoit.miyamoto.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.example.justdoit.miyamoto.Model.WishListModel

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.activity.MainActivity
import com.example.justdoit.miyamoto.activity.PaisluActivity
import com.example.justdoit.miyamoto.activity.TabActivity
import com.example.justdoit.miyamoto.adapter.WishListAdapter
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
    var shoppingListId = 0
    var mWishlistAdapter: WishListAdapter? = null
    var mWishlist: ListView? = null

    var containerOk:LinearLayout?=null

    var cardV:CardView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pasilu, container, false)
        mWishlist = view.findViewById(R.id.list_wish)
        mWishlistAdapter = WishListAdapter(context!!, R.layout.item_wishlist)

        token=arguments!!.getString("token")
        userId=arguments!!.getInt("id")
        shoppingListId = arguments!!.getInt("shoppingListId")

        val pasiluBtn = view.findViewById<Button>(R.id.pasilu)
        fetchShoppingList(shoppingListId)

        containerOk=view.findViewById(R.id.container_ok)
        containerOk?.visibility=View.GONE

        cardV=view.findViewById(R.id.card_view)
        cardV?.visibility=View.VISIBLE

        pasiluBtn.setOnClickListener {

            containerOk?.visibility=View.VISIBLE
            cardV?.visibility=View.GONE
            val okBtn=view.findViewById<TextView>(R.id.text_ok)
            okBtn.setOnClickListener {
                containerOk?.visibility=View.GONE
                cardV?.visibility=View.VISIBLE
            }

            // todo ここでpostするデータ付与して
            val formBody = FormBody.Builder()
                    .add("token", token)
                    .add("pasiRequestId", userId.toString())
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
                    (context as AppCompatActivity).runOnUiThread {
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
                        val result = json.getJSONArray("result")?.let {
                            val maxCount = it.length()
                            val result: Array<WishListModel?> = arrayOfNulls(maxCount)
                            for (i in 0 until maxCount) {
                                val obj = it[i] as JSONObject
                                result[i] = WishListModel(obj.getString("title"), obj.getInt("count"))
                            }
                            return@let result
                        }
                        result?.forEach {
                            mWishlistAdapter?.add(it!!)
                        }
                        mWishlist?.adapter = mWishlistAdapter

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

}// Required empty public constructor

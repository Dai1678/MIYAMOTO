package com.example.justdoit.miyamoto.fragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.justdoit.miyamoto.Model.WishListModel
import com.example.justdoit.miyamoto.Pasilist.PasilistAdapter
import com.example.justdoit.miyamoto.Pasilist.PasilistModel
import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.Unit.MatchingTimerTask
import com.example.justdoit.miyamoto.activity.MainActivity
import com.example.justdoit.miyamoto.activity.TabActivity
import com.example.justdoit.miyamoto.activity.WishListActivity
import com.example.justdoit.miyamoto.adapter.WishListAdapter
import kotlinx.android.synthetic.main.fragment_wish_list.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import okhttp3.RequestBody
import kotlin.collections.ArrayList


class WishListFragment : Fragment() {

    var mWishlistAdapter: WishListAdapter? = null
    var mWishlist: ListView? = null

    var timer: Timer? = null

    var token: String? = null
    var timelimit: String? = null
    var location: String? = null
    var totalAmount = 0

    companion object {
        fun getInstance(): WishListFragment {
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

        val sharedPreferences = this.activity!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", "")

        mWishlist = view.findViewById(R.id.list_wish)
        mWishlistAdapter = WishListAdapter(context!!, R.layout.item_wishlist)

//        for (i in 0..9) {
//            val sample = WishListModel("カップ麺", i)
//            mWishlistAdapter?.add(sample)
//        }
        val sample = WishListModel("", 0)
        sample.isBottom = true
        mWishlistAdapter?.add(sample)

        mWishlist?.adapter = mWishlistAdapter

        val settingLocation = view.findViewById<TextView>(R.id.setting_location)

        val containerSetting = view.findViewById<LinearLayout>(R.id.container_list_setting)
        containerSetting.setOnClickListener {
            val editView = EditText(view.context)

            val addDialog = AlertDialog.Builder(view.context)

            addDialog.setTitle("設定")
            addDialog.setMessage("集合場所を入力してください")
            addDialog.setView(editView)
            // OKボタンの設定
            addDialog.setPositiveButton("決定", { dialog: DialogInterface, whichButton: Int ->
                settingLocation.text = editView.text.toString()
            })
            // キャンセルボタンの設定
            addDialog.setNegativeButton("キャンセル", { dialog: DialogInterface, whichButton: Int ->
            })
            addDialog.show()
        }

        timelimit = "2018-04-21 21:30"
        location = settingLocation.text.toString()
        totalAmount = 2000

        mWishlist?.setOnItemClickListener { adapterView, view, i, l ->
            if(i==mWishlistAdapter?.count!!-1){
                val sample = WishListModel("", 1)
                sample.isBottom=false
                mWishlistAdapter?.setItem(i,sample)
                val addBtn = WishListModel("", 1)
                addBtn.isBottom = true
                mWishlistAdapter?.add(addBtn)
                mWishlistAdapter?.notifyDataSetChanged()
            }else{
                val editView = EditText(view.context)

                val addDialog = AlertDialog.Builder(view.context)

                addDialog.setTitle("商品名")
                addDialog.setMessage("商品名を入力してください")
                addDialog.setView(editView)
                // OKボタンの設定
                addDialog.setPositiveButton("決定", { dialog: DialogInterface, whichButton: Int ->
                    val item=mWishlistAdapter?.getItem(i)
                    item?.title=editView.text.toString()
                    mWishlistAdapter?.setItem(i,item!!)
                    val array=ArrayList<WishListModel>()
                    for (pos in 0 until mWishlistAdapter?.count!!){
                        array.add(mWishlistAdapter?.getItem(pos)!!)
                    }
                    mWishlistAdapter?.clear()
                    for (pos in 0 until array.size){
                        mWishlistAdapter?.add(array[pos])
                    }
                    mWishlist?.adapter=mWishlistAdapter
                })
                // キャンセルボタンの設定
                addDialog.setNegativeButton("キャンセル", { dialog: DialogInterface, whichButton: Int ->
                })
                addDialog.show()
            }
        }

        val createBtn = view.findViewById<Button>(R.id.createWish)
        createBtn.setOnClickListener {
            if (mWishlistAdapter?.count!! > 1) {
                val url = "http://140.82.9.44:3000/match/request"
                // todo ここでpostするデータ付与して
                val formBody = FormBody.Builder().apply {
                    add("token", token)
                    add("timeLimit", timelimit)
                    add("address", location)
                    add("totalAmount", totalAmount.toString())
                    add("shoppingLists[0]", "aa")
                }.build()

                val JSON = MediaType.parse("application/json; charset=utf-8")
                var json = "{\n" +
                        "    \"token\": \"$token\",\n" +
                        "    \"timeLimit\": \"$timelimit\",\n" +
                        "    \"address\": \"$location\",\n" +
                        "    \"totalAmount\": $totalAmount,\n" +
                        "    \"shoppingLists\": [\n"
                for (i in 0..(mWishlistAdapter?.count!! - 2)) {
                    val title=mWishlistAdapter?.getItem(i)?.title
                    val cnt=mWishlistAdapter?.getItem(i)?.count
                    if (i == (mWishlistAdapter?.count!! - 2)) {
                        json=json+
                                "        {\n" +
                                "            \"title\": \"$title\",\n" +
                                "            \"count\": $cnt\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}"
                    } else {
                        json=json+
                                "        {\n" +
                                "            \"title\": \"$title\",\n" +
                                "            \"count\": $cnt\n" +
                                "        }\n"
                    }
                }
                val body = RequestBody.create(JSON, json)

                val request = Request.Builder()
                        .url(url)       // HTTPアクセス POST送信 テスト確認用ページ
                        .post(body)
                        .build()

                val client = OkHttpClient()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {

                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        val res = response.body()?.string()
                        (context as WishListActivity).runOnUiThread {
                            val json: JSONObject
                            try {

                                timer = Timer()
                                val timerTask = MatchingTimerTask(context!!, token!!, timer!!)
                                timer?.scheduleAtFixedRate(timerTask, 0, 5000)

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                    }
                })

                val sharedPreferences = activity?.getSharedPreferences("Setting",Context.MODE_PRIVATE)
                val shardPrefEditor = sharedPreferences?.edit()
                shardPrefEditor?.putBoolean("mode", true)
                shardPrefEditor?.apply()

                val intent=Intent(context,TabActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
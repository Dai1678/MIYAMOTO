package com.example.justdoit.miyamoto.Unit

import android.content.Context
import okhttp3.*
import org.json.JSONException
import android.widget.Toast
import com.example.justdoit.miyamoto.activity.MainActivity
import org.json.JSONObject
import okhttp3.OkHttpClient
import java.io.IOException
import android.widget.TextView
import okhttp3.FormBody
import okhttp3.RequestBody




/**
 * Created by taiga on 2018/04/21.
 */
class OkHttpSample {
    var url = "http://weather.livedoor.com/forecast/webservice/json/v1?city=400040"

    //get
    public fun get(context: Context) {
        val request = Request.Builder()
                .url(url)     // 130010->東京
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
                        val massage = json.getString("pinpointLocations")
                        Toast.makeText(context,massage,Toast.LENGTH_LONG).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    //post
    fun post(context: Context){
        // todo ここでpostするデータ付与して
        val formBody = FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client_id", "d643130e33cc5e1ac0692d40f97d3b0baac499cf8d549b66830bb54137888aad")
                .add("client_secret", "1546d49ba798b512f8493e8a9e87b01313b01f3f69d62d87c00773068e4bd233")
                .build()

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
                (context as MainActivity).runOnUiThread{
                    val json: JSONObject
                    try {
                        json = JSONObject(res)
                        val massage = json.getString("massage")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}
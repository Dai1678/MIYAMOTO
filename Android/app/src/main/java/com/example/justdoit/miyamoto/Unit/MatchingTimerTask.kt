package com.example.justdoit.miyamoto.Unit

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import java.util.*
import com.example.justdoit.miyamoto.activity.MainActivity
import com.example.justdoit.miyamoto.activity.MatchingCompleteActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


/**
 * Created by taiga on 2018/04/21.
 */
class MatchingTimerTask(context: Context,val token:String,val timer:Timer) : TimerTask() {
    private var handler: Handler? = null
    private var context: Context? = null

    init {
        handler = Handler()
        this.context = context
    }

    override fun run() {
        val url="http://140.82.9.44:3000/match/checkFlag?token=$token"
        handler?.post {
            val request = Request.Builder()
                    .url(url)     // 130010->東京
                    .get()
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("aa","失敗")
                }

                @Throws(IOException::class)
                override fun
                        onResponse(call: Call, response: Response) {
                    val res = response.body()?.string()
                    (context as AppCompatActivity).runOnUiThread{
                        val json: JSONObject
                        try {
                            json = JSONObject(res)
                            val isMatched = json.getBoolean("isMatched")
                            Log.d("MATCH",isMatched.toString())
                            if(isMatched) {

                                timer.cancel()
                                val sharedPreferences = context?.getSharedPreferences("Setting", Context.MODE_PRIVATE)
                                val shardPrefEditor = sharedPreferences?.edit()
                                shardPrefEditor?.putBoolean("mode", false)
                                shardPrefEditor?.apply()

                                //マッチング完了画面へ遷移
                                val intent = Intent(context, MatchingCompleteActivity::class.java)
                                context?.startActivity(intent)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            })
        }
    }
}
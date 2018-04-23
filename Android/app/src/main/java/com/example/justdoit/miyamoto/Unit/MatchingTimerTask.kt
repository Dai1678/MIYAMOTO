package com.example.justdoit.miyamoto.Unit

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.JsonObject
import com.example.justdoit.miyamoto.ApiClient
import java.util.*
import com.example.justdoit.miyamoto.activity.MainActivity
import com.example.justdoit.miyamoto.activity.MatchingCompleteActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
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
        launch(UI) {
            ApiClient.shared.checkMatchedFlag().await()?.let {
                handler(it)
            }
        }
    }

    private fun handler(json: JsonObject) {
        val isMatched = json.boolean("isMatched")!!
        if (!isMatched) return

        timer.cancel()
        val sharedPreferences = context?.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val shardPrefEditor = sharedPreferences?.edit()
        shardPrefEditor?.putBoolean("mode", false)
        shardPrefEditor?.putBoolean("notComplete", true)
        shardPrefEditor?.apply()

        val pasiRequestId = json.string("pasiRequestId")
        shardPrefEditor?.putString("pasiRequestId", pasiRequestId)
        shardPrefEditor?.apply()

        //マッチング完了画面へ遷移
        val intent = Intent(context, MatchingCompleteActivity::class.java)
        context?.startActivity(intent)
    }
}
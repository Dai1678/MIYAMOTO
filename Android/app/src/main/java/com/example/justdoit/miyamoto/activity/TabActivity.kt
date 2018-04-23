package com.example.justdoit.miyamoto.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.justdoit.miyamoto.ApiClient
import com.example.justdoit.miyamoto.Pasilist.PasilistActivity
import com.example.justdoit.miyamoto.Pasilist.PasilistFragment
import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.R.id.pager
import com.example.justdoit.miyamoto.R.id.tabLayout
import com.example.justdoit.miyamoto.Unit.ViewPagerAdapter
import com.example.justdoit.miyamoto.fragment.MyProfileFragment
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class TabActivity : AppCompatActivity(), MyProfileFragment.OnMyProfileListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        val sharedPreferences = this.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val mode = sharedPreferences.getBoolean("mode", false)
        val notComplete = sharedPreferences.getBoolean("notComplete", false)
        val token=sharedPreferences.getString("token", "")
        val pasiRequestId=sharedPreferences.getString("pasiRequestId", "")

        val matchingV=findViewById<TextView>(R.id.is_matching)

        if(mode or notComplete){
            tabLayout.visibility=View.GONE
            matchingV.visibility= View.VISIBLE
            if(mode){
                matchingV.text="マッチング中..."
            }
            else if(notComplete){
                matchingV.text="決済完了したらタップ"
                matchingV.setOnClickListener {
                    // todo ここでpostするデータ付与して
                    launch(UI) {
                        ApiClient.shared.received(pasiRequestId).await()?.let {
                            finishPayment(matchingV)
                        }
                    }
                }
            }
        } else{
            matchingV.visibility=View.GONE
            tabLayout.visibility=View.VISIBLE
        }

        val tabNames = arrayOf("Pasilitator", "Pasilist")

        val fragmentManager = supportFragmentManager
        val viewPagerAdapter = ViewPagerAdapter(fragmentManager, tabNames)

        pager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(pager)

    }

    override fun intentWish() {
        val intent = Intent(this, WishListActivity::class.java)
        startActivity(intent)
    }

    private fun finishPayment(matchingV: TextView) {
        val sharedPreferences = getSharedPreferences("Setting",Context.MODE_PRIVATE)
        val shardPrefEditor = sharedPreferences?.edit()
        shardPrefEditor?.putString("pasiRequestId", "")
        shardPrefEditor?.putBoolean("notComplete", false)
        shardPrefEditor?.apply()

        matchingV.visibility=View.GONE
        tabLayout.visibility=View.VISIBLE
    }
}

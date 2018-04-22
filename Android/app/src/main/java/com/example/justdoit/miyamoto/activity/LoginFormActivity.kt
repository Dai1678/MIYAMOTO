package com.example.justdoit.miyamoto.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.justdoit.miyamoto.R

class LoginFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        val sharedPreferences = this.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        val shardPrefEditor = sharedPreferences?.edit()
        shardPrefEditor?.putBoolean("mode", false)
        shardPrefEditor?.apply()
    }
}

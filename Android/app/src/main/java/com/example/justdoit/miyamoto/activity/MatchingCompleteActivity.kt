package com.example.justdoit.miyamoto.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.justdoit.miyamoto.Pasilist.PasilistActivity
import com.example.justdoit.miyamoto.R

class MatchingCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_complete)
    }

    fun agree(v: View){
        val intent=Intent(this,PasilistActivity::class.java)
        startActivity(intent)
    }
}

package com.example.justdoit.miyamoto.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)  {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment, MainFragment.getInstance())
                    .addToBackStack(null)
                    .commit()
        }

    }
}

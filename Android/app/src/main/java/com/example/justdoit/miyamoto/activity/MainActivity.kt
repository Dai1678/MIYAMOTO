package com.example.justdoit.miyamoto.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.justdoit.miyamoto.Pasilist.PasilistActivity
import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if(savedInstanceState == null)  {
//            supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.main_fragment, MainFragment.getInstance())
//                    .commit()
//        }
        // Fragmentを作成します
        val fragment = MainFragment()
        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
        val transaction = supportFragmentManager.beginTransaction()
        // 新しく追加を行うのでaddを使用します
        transaction.add(R.id.container_fragment, fragment)
        // 最後にcommitを使用することで変更を反映します
        transaction.commit()

    }

    fun intentLogin(v: View){
        val intent=Intent(this,LoginFormActivity::class.java)
        startActivity(intent)
    }

    fun intentWishlist(v:View){
        val intent=Intent(this,WishListActivity::class.java)
        startActivity(intent)
    }
}

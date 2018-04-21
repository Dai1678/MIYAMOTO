package com.example.justdoit.miyamoto.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.fragment.PasiluFragment
import com.example.justdoit.miyamoto.fragment.WishListFragment

class PaisluActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paislu)

        val id=intent.getIntExtra("id",0)
        val token=intent.getStringExtra("token")

        // Fragmentを作成します
        val fragment = PasiluFragment()
        fragment.arguments?.putInt("id",id)
        fragment.arguments?.putString("token",token)
        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
        val transaction = supportFragmentManager.beginTransaction()
        // 新しく追加を行うのでaddを使用します
        transaction.add(R.id.container_pasilu, fragment)
        // 最後にcommitを使用することで変更を反映します
        transaction.commit()
    }

    fun back(v: View){
        finish()
    }
}

package com.example.justdoit.miyamoto.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.fragment.MainFragment
import com.example.justdoit.miyamoto.fragment.WishListFragment

class WishListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

        // Fragmentを作成します
        val fragment = WishListFragment()
        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
        val transaction = supportFragmentManager.beginTransaction()
        // 新しく追加を行うのでaddを使用します
        transaction.add(R.id.container_wishlist, fragment)
        // 最後にcommitを使用することで変更を反映します
        transaction.commit()
    }
}

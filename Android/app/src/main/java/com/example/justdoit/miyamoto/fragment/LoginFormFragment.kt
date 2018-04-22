package com.example.justdoit.miyamoto.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.justdoit.miyamoto.ApiClient
import com.example.justdoit.miyamoto.Pasilist.PasilistAdapter

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.Unit.OkHttpSample
import com.example.justdoit.miyamoto.activity.LoginFormActivity
import com.example.justdoit.miyamoto.activity.MainActivity
import com.example.justdoit.miyamoto.activity.TabActivity
import kotlinx.android.synthetic.main.fragment_login_form.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class LoginFormFragment : Fragment(), View.OnClickListener {

    private lateinit var editUserName : String
    private lateinit var editUserPass : String

    companion object {
        fun getInstance() : LoginFormActivity {
            return LoginFormActivity()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_login_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        val strUserAddress = userName.text.toString()
        val strUserPass = userPass.text.toString()

        if (strUserAddress != "" || strUserPass != ""){
            //認証作業
            launch(UI) {
                ApiClient.shared.login(strUserAddress, strUserPass).await()
            }
            val intent = Intent(context, TabActivity::class.java)
            startActivity(intent)
        }else{
            Snackbar.make(view, "入力してください", Snackbar.LENGTH_SHORT)
                    .setAction("Action",null).show()
        }
    }

}

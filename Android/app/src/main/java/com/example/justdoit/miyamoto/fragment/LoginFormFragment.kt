package com.example.justdoit.miyamoto.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.Unit.OkHttpSample
import kotlinx.android.synthetic.main.fragment_login_form.*
import okhttp3.OkHttpClient

class LoginFormFragment : Fragment(), View.OnClickListener {

    private lateinit var editUserName : String
    private lateinit var editUserPass : String

    companion object {
        fun getInstance() : MainFragment {
            return MainFragment()
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
        val strUserName = userName.text.toString()
        val strUserPass = userPass.text.toString()

        if (strUserName == "" || strUserPass == ""){
            runLogin(view,false)
        }else{
            runLogin(view,true)
        }
    }

    private fun runLogin(view: View, result : Boolean){
        when(result){
            true -> {
                //認証作業
                val httpClient = OkHttpSample()
                httpClient.post(context!!)

            }

            false -> {
                Snackbar.make(view, "入力してください", Snackbar.LENGTH_SHORT)
                        .setAction("Action",null).show()
            }
        }
    }
}

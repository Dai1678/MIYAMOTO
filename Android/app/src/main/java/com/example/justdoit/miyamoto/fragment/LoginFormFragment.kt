package com.example.justdoit.miyamoto.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.Unit.OkHttpSample
import kotlinx.android.synthetic.main.fragment_login_form.*

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
        val strUserAddress = userName.text.toString()
        val strUserPass = userPass.text.toString()

        if (strUserAddress != "" || strUserPass != ""){
            runLogin(strUserAddress, strUserPass)
        }else{
            failureLogin(view)
        }
    }

    private fun runLogin(address: String, pass: String){
        //認証作業
        val httpClient = OkHttpSample()
        httpClient.postLoginData(context!!, address, pass)
    }

    private fun failureLogin(view: View){
        Snackbar.make(view, "入力してください", Snackbar.LENGTH_SHORT)
                .setAction("Action",null).show()
    }
}

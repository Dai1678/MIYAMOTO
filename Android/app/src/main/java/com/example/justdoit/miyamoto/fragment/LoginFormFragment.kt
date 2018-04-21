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
import com.example.justdoit.miyamoto.Pasilist.PasilistAdapter

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.Unit.OkHttpSample
import com.example.justdoit.miyamoto.activity.LoginFormActivity
import com.example.justdoit.miyamoto.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_login_form.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

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

        //todo
        val intent=Intent(context,MainActivity::class.java)
        startActivity(intent)

        if (strUserAddress != "" || strUserPass != ""){
            //認証作業
            postLoginData(strUserAddress, strUserPass)
        }else{
            Snackbar.make(view, "入力してください", Snackbar.LENGTH_SHORT)
                    .setAction("Action",null).show()
        }
    }

    private fun postLoginData(email: String, pass: String){
        //Login認証データ
        val formBody = FormBody.Builder()
                .add("email", email)
                .add("password", pass)
                .build()

        val request = Request.Builder()
                .url("http://140.82.9.44:3000/auth/login")       // HTTPアクセス POST送信 テスト確認用ページ
                .post(formBody)
                .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val res = response.body()?.string()
                (context as LoginFormActivity).runOnUiThread{
                    val json: JSONObject
                    try {
                        json = JSONObject(res)
                        val token = json.getString("token")
                        Log.i("token",token)
                        saveToken("token", token)
                        val intent=Intent(context,MainActivity::class.java)
                        startActivity(intent)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    private fun saveToken(key: String, strToken: String){
        val sharedPreferences = this.activity!!.getSharedPreferences("Setting",Context.MODE_PRIVATE)
        val shardPrefEditor = sharedPreferences.edit()

        shardPrefEditor.putString(key, strToken)
        shardPrefEditor.apply()
        Log.i("token","保存完了！")
    }

}

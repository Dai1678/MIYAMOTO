package com.example.justdoit.miyamoto.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.activity.MainActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PasiluFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PasiluFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PasiluFragment : Fragment() {

    var token=""
    var userId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pasilu, container, false)

        token=arguments!!.getString("token")
        userId=arguments!!.getInt("id")

        val pasiluBtn = view.findViewById<Button>(R.id.pasilu)

        pasiluBtn.setOnClickListener {

            // todo ここでpostするデータ付与して
            val formBody = FormBody.Builder()
                    .add("token", token)
                    .add("id", userId.toString())
                    .build()

            val url="http://140.82.9.44:3000/match/acceptRequest"
            val request = Request.Builder()
                    .url(url)       // HTTPアクセス POST送信 テスト確認用ページ
                    .post(formBody)
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val res = response.body()?.string()
                    (context as AppCompatActivity).runOnUiThread {
                        val json: JSONObject
                        try {
                            json = JSONObject(res)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            })
        }

        return view
    }

}// Required empty public constructor

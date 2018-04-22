package com.example.justdoit.miyamoto

import android.provider.ContactsContract
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.json.JSONObject

class ApiClient private constructor() {
    private val client = Fuel
    private val SERVER_BASE_URL = "http://140.82.9.44:3000"
    private val parser = Parser()

    private object Holder {
        val INSTANCE = ApiClient()
    }

    companion object {
        val shared: ApiClient by lazy { Holder.INSTANCE }
    }

    private fun createUrl(endPoint: String, params: HashMap<String, Any>?): String {
        val paramString = params?.let {
            var query = ""
            it.forEach {query += "&${it.key}=${it.value}"}
            return@let query
        }
        return "${SERVER_BASE_URL}$endPoint?token=${Session.shared.token}$paramString"
    }

    private fun createJsonWithToken(json: JSONObject): String {
        return json?.apply { put("token", Session.shared.token) }.toString()
    }

    private fun getWithToken(endPoint: String, params: HashMap<String, Any>): String? {
        var res: String? = null
        client.get(createUrl(endPoint, params)).responseString { request, response, result ->
            result.fold({ data ->
                res = data
            }, { err ->
                res = null
            })
        }
        return res
    }

    private fun postWithToken(endPoint: String, postJson: JSONObject): String? {
        var res: String? = null
        client.post(SERVER_BASE_URL + endPoint).body(createJsonWithToken(postJson)).responseString { request, response, result ->
            result.fold({ data ->
                res = data
            }, { err ->
                res = null
            })
        }
        return res
    }

    fun login(email: String, password: String): Deferred<Boolean> = async(CommonPool) {
        val post = JSONObject().apply {
            put("email", email)
            put("password", password)
        }.toString()
        client.post("$SERVER_BASE_URL/auth/login").body(post).responseString { request, response, result ->
            result.fold({ data ->
                val json = parser.parse(data) as JSONObject
                if (json.getInt("ok") == 1) Session.shared.token = json.getString("token")
            }, { err ->
                err.printStackTrace()
            })
        }
        return@async Session.shared.token.isBlank()
    }
}
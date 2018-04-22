package com.example.justdoit.miyamoto

import android.location.Address
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.example.justdoit.miyamoto.Pasilist.PasilistModel
import com.example.justdoit.miyamoto.model.WishListModel
import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.json.JSONObject

class ApiClient private constructor() {
    private val client = Fuel
    private val SERVER_BASE_URL = "http://140.82.9.44:3000"
    private val parser = Parser()
    private val klaxon = Klaxon()

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

    private fun createJsonWithToken(json: JsonObject): String {
        return json?.apply { put("token", Session.shared.token) }.toString()
    }

    private fun getWithToken(endPoint: String, params: HashMap<String, Any>?): String? {
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

    private fun postWithToken(endPoint: String, postJson: JsonObject): String? {
        var res: String? = null
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
        client.post(SERVER_BASE_URL + endPoint).header(header).body(createJsonWithToken(postJson)).responseString { request, response, result ->
            result.fold({ data ->
                res = data
            }, { err ->
                res = null
            })
        }
        return res
    }

    fun login(email: String, password: String): Deferred<Boolean> = async(CommonPool) {
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
        val post = JSONObject().apply {
            put("email", email)
            put("password", password)
        }.toString()
        client.post("$SERVER_BASE_URL/auth/login").header(header).body(post).responseString { request, response, result ->
            result.fold({ data ->
                val json = parser.parse(StringBuilder(data)) as JsonObject
                if (json.int("ok") == 1) Session.shared.token = json.string("token")!!
            }, { err ->
                err.printStackTrace()
            })
        }
        return@async !Session.shared.token.isBlank()
    }

    fun fetchPasiList(): Deferred<MutableList<PasilistModel>?> = async(CommonPool) {
        return@async getWithToken("/match/pasilist", null)?.let {
            val json = parser.parse(StringBuilder(it)) as JsonObject
            return@let json.array<PasilistModel>("result")
        }
    }

    fun postPasiRequest(timeLimit: String, address: String, totalAmount: Int, shoppingLists: MutableList<WishListModel>): Deferred<Boolean> = async(CommonPool) {
        val postJson = JsonObject().apply {
            put("timeLimit", timeLimit)
            put("address", address)
            put("totalAmount", totalAmount)
            put("shoppingLists", shoppingLists)
        }
        postWithToken("/match/request", postJson).let {
            if (it == null) return@async false
            val json = parser.parse(StringBuilder(it)) as JsonObject
            return@async json.int("ok") == 1
        }
    }
}
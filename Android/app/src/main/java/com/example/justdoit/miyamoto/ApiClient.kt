package com.example.justdoit.miyamoto

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.example.justdoit.miyamoto.Pasilist.PasilistModel
import com.example.justdoit.miyamoto.model.WishListModel
import com.github.kittinunf.fuel.Fuel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.json.JSONArray
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

    private fun createJsonWithToken(json: JsonObject): String {
        return json?.apply { put("token", Session.shared.token) }.toJsonString()
    }

    private fun getWithToken(endPoint: String, params: HashMap<String, Any>?): String? {
        val (_, _, result) = client.get(createUrl(endPoint, params)).responseString()
        result.fold({ data ->
            return data
        }, { e ->
            e.printStackTrace()
            return null
        })
    }

    private fun postWithToken(endPoint: String, postJson: JsonObject): String? {
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
        val hoge = createJsonWithToken(postJson)
        val (_, _, result) = client.post(SERVER_BASE_URL + endPoint).header(header).body(createJsonWithToken(postJson)).responseString()
        result.fold({ data ->
            return data
        }, { err ->
            err.printStackTrace()
            return null
        })
    }

    fun login(email: String, password: String): Deferred<String?> = async(CommonPool) {
        val header: HashMap<String, String> = hashMapOf("Content-Type" to "application/json")
        val post = JSONObject().apply {
            put("email", email)
            put("password", password)
        }.toString()

        val (_, _, result) = client.post("$SERVER_BASE_URL/auth/login").header(header).body(post).responseString()
        result.fold({ data ->
            val json = parser.parse(StringBuilder(data)) as JsonObject
            if (json.int("ok") == 1) return@async json.string("token")!!
            return@async null
        }, { err ->
            err.printStackTrace()
            return@async null
        })
    }

    fun fetchPasiList(): Deferred<MutableList<PasilistModel>?> = async(CommonPool) {
        getWithToken("/match/pasilist", null)?.let {
            val json = parser.parse(StringBuilder(it)) as JsonObject
            return@async mutableListOf<PasilistModel>().apply {
                json.array<JsonObject>("result")?.forEach {
                    add(PasilistModel(
                            it.int("id")!!,
                            it.int("userId")!!,
                            it.string("address")!!,
                            it.string("timeLimit")!!,
                            it.int("totalAmount")!!,
                            it.int("shoppingListId")!!
                    ))
                }
            }
        }
    }

    fun postPasiRequest(timeLimit: String, address: String, totalAmount: Int, shoppingLists: MutableList<WishListModel>): Deferred<Boolean> = async(CommonPool) {
        val postJson = JsonObject().apply {
            val jsonLists = JSONArray().apply {
                shoppingLists.forEach {
                    put(JSONObject().apply {
                        put("title", it.title)
                        put("count", it.count)
                    })
                }
            }

            put("timeLimit", timeLimit)
            put("address", address)
            put("totalAmount", totalAmount)
            put("shoppingLists", jsonLists)
        }
        postWithToken("/match/request", postJson).let {
            if (it == null) return@async false
            val json = parser.parse(StringBuilder(it)) as JsonObject
            return@async json.int("ok") == 1
        }
    }
}
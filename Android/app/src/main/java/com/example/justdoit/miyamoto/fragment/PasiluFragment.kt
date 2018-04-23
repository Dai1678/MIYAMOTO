package com.example.justdoit.miyamoto.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.example.justdoit.miyamoto.ApiClient
import com.example.justdoit.miyamoto.model.WishListModel

import com.example.justdoit.miyamoto.R
import com.example.justdoit.miyamoto.activity.PaisluActivity
import com.example.justdoit.miyamoto.adapter.WishListAdapter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
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

    var token = ""
    var userId = 0
    var shoppingListId = 0
    var mWishlistAdapter: WishListAdapter? = null
    var mWishlist: ListView? = null

    var containerOk: LinearLayout? = null

    var cardV: CardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pasilu, container, false)
        mWishlist = view.findViewById(R.id.list_wish)
        mWishlistAdapter = WishListAdapter(context!!, R.layout.item_wishlist)

        token = arguments!!.getString("token")
        userId = arguments!!.getInt("id")
        shoppingListId = arguments!!.getInt("shoppingListId")

        val pasiluBtn = view.findViewById<Button>(R.id.pasilu)
        launch(UI) {
            ApiClient.shared.fetchShoppingList(shoppingListId).await()?.let {
                it.forEach {
                    mWishlistAdapter?.add(it!!)
                }
                mWishlist?.adapter = mWishlistAdapter
            }
        }

        containerOk = view.findViewById(R.id.container_ok)
        containerOk?.visibility = View.GONE

        cardV = view.findViewById(R.id.card_view)
        cardV?.visibility = View.VISIBLE

        pasiluBtn.setOnClickListener {

            containerOk?.visibility = View.VISIBLE
            cardV?.visibility = View.GONE
            val okBtn = view.findViewById<TextView>(R.id.text_ok)
            okBtn.setOnClickListener {
                containerOk?.visibility = View.GONE
                cardV?.visibility = View.VISIBLE
            }

            // todo ここでpostするデータ付与して
            launch(UI) {
                ApiClient.shared.acceptPasiRequest(userId).await()
            }
        }

        return view
    }
}// Required empty public constructor

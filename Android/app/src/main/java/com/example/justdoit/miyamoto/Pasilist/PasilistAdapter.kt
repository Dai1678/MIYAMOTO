package com.example.justdoit.miyamoto.Pasilist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.justdoit.miyamoto.R

/**
 * Created by taiga on 2018/04/21.
 */
class PasilistAdapter(context: Context,resource: Int) :ArrayAdapter<PasilistModel>(context,resource) {
    private var mResource: Int
    private var mItems: ArrayList<PasilistModel>
    private var mInflater: LayoutInflater

    init {
        mResource = resource
        mItems = ArrayList<PasilistModel>()
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun reverse(){
        mItems.reverse()
    }

    override fun add(item:PasilistModel){
        mItems.add(item)
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getItem(position: Int): PasilistModel? {
        return mItems.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (convertView != null) {
            view = convertView
        } else {
            view = mInflater.inflate(mResource, null)
        }

        // リストビューに表示する要素を取得
        val item = mItems[position]

        val locationT=view.findViewById<TextView>(R.id.location_pasilist)
        locationT.text=item.address
        val timelimitT=view.findViewById<TextView>(R.id.timelimit_pasilist)
        timelimitT.text=item.timeLimit
        val amountT=view.findViewById<TextView>(R.id.amount_pasilist)
        amountT.text=item.totalAmount.toString()

        val idT=view.findViewById<TextView>(R.id.text_id)
        idT.text=item.userId.toString()


        return view
    }
}
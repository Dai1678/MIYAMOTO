package com.example.justdoit.miyamoto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.justdoit.miyamoto.Model.WishListModel
import com.example.justdoit.miyamoto.Pasilist.PasilistModel
import com.example.justdoit.miyamoto.R

/**
 * Created by taiga on 2018/04/22.
 */
class WishListAdapter(context: Context,resource:Int) :ArrayAdapter<WishListModel>(context,resource) {
    private var mResource: Int
    private var mItems: ArrayList<WishListModel>
    private var mInflater: LayoutInflater

    var titleV:EditText?=null
    var cntV:EditText?=null
    var delBtn:Button?=null
    var reloadBtn:Button?=null

    var mainC:LinearLayout?=null
    var addC:TextView?=null

    init {
        mResource = resource
        mItems = ArrayList<WishListModel>()
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun reverse(){
        mItems.reverse()
    }

    fun setItem(pos:Int,item:WishListModel){
        mItems.set(pos,item)
    }

    fun reload() {
        clear()
        addAll(mItems)
        notifyDataSetChanged()
    }

    override fun add(item: WishListModel){
        mItems.add(item)
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getItem(position: Int): WishListModel? {
        return mItems.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        // リストビューに表示する要素を取得
        val item = mItems[position]

        if (convertView != null) {
            view = convertView

        } else {
            view = mInflater.inflate(mResource, null)

            titleV=view.findViewById<EditText>(R.id.edit_title)
            cntV=view.findViewById<EditText>(R.id.edit_count)
            delBtn=view.findViewById<Button>(R.id.delete_btn)
            reloadBtn=view.findViewById<Button>(R.id.reload_btn)

            mainC=view.findViewById<LinearLayout>(R.id.container_main)
            addC=view.findViewById<TextView>(R.id.container_add)
        }

        titleV?.setText(item.title)
        cntV?.setText(item.count.toString())
        delBtn?.setOnClickListener {
            //mItems.remove(item)
        }
        reloadBtn?.setOnClickListener {
            item.title=titleV?.text.toString()
            item.count=cntV?.text.toString().toInt()
            mItems.set(position,item)
        }

        if(item.isBottom){
            mainC?.visibility=View.GONE
            addC?.visibility=View.VISIBLE
        }
        else{
            mainC?.visibility=View.VISIBLE
            addC?.visibility=View.GONE
        }

        return view
    }
}
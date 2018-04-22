package com.example.justdoit.miyamoto.model

/**
 * Created by taiga on 2018/04/22.
 */
class WishListModel(title:String,count:Int) {
    var title:String=""
    var count:Int=0
    var isBottom=false

    init {
        this.title=title
        this.count=count
    }
}
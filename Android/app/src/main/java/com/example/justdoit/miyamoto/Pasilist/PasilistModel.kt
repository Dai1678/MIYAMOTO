package com.example.justdoit.miyamoto.Pasilist

/**
 * Created by taiga on 2018/04/21.
 */
class PasilistModel(userId:Int,location:String,timeLimit:String,money:Int) {
    var userId:Int=-1
    var location:String=""
    var timeLimit:String=""
    var amount:Int=0

    init {
        this.userId=userId
        this.location=location
        this.timeLimit=timeLimit
        this.amount =money
    }
}
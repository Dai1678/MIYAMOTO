package com.example.justdoit.miyamoto.Pasilist

/**
 * Created by taiga on 2018/04/21.
 */
class PasilistModel() {
    var userId:Int=-1
    var location:String=""
    var timeLimit:String=""
    var amount:Int=0

    constructor(userId:Int,location:String,timeLimit:String,money:Int):this(){
        this.userId=userId
        this.location=location
        this.timeLimit=timeLimit
        this.amount =money
    }
}
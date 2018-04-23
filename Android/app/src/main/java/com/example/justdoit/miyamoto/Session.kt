package com.example.justdoit.miyamoto

class Session private constructor() {
    var token = ""
    var inprogressRequestId: Int? = null

    private object Holder {
        val INSTANCE = Session()
    }

    companion object {
        val shared: Session by lazy { Holder.INSTANCE }
    }
}
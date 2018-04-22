package com.example.justdoit.miyamoto

class Session private constructor() {
    var token = ""

    private object Holder {
        val INSTANCE = Session()
    }

    companion object {
        val shared: Session by lazy { Holder.INSTANCE }
    }
}
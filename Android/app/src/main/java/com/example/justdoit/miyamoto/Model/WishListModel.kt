package com.example.justdoit.miyamoto.model

/**
 * Created by taiga on 2018/04/22.
 */

data class WishListModel(
        val id: Int = 0,
        var title: String = "",
        var count: Int = 1,
        var isBottom: Boolean = false
)
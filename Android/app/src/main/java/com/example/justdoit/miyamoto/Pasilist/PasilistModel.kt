package com.example.justdoit.miyamoto.Pasilist

/**
 * Created by taiga on 2018/04/21.
 */

data class PasilistModel(
        val id: Int,
        val userId: Int,
        val address: String,
        val timeLimit: String,
        val totalAmount: Int,
        val shoppingListId: Int
)

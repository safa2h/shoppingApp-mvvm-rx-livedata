package com.devsa.nikestore4.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val discount: Int,
    val id: Int,
    val image: String,
    val previous_price: Int,
    val price: Int,
    val status: Int,
    val title: String
):Parcelable

const val SORTlATEST=0
const val SORT_POPULAR=1
const val SORT_PRICE_DESC=2
const val SORT_PRICE_ASC=3
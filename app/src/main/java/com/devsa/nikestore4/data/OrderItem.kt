package com.devsa.nikestore4.data

import com.devsa.nikestore4.data.Product

data class OrderItem(
    val count: Int,
    val discount: Int,
    val id: Int,
    val order_id: Int,
    val price: Int,
    val product: Product,
    val product_id: Int
)
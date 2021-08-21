package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.AddToCartResponse
import com.devsa.nikestore4.data.CartItemCount
import com.devsa.nikestore4.data.CartResponse
import com.devsa.nikestore4.data.MessageResponse
import io.reactivex.Single

interface CartDataSource {

    fun addToCart(productId:Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItemId:Int): Single<MessageResponse>
    fun changeCount(cartItemId:Int,count:Int): Single<AddToCartResponse>
    fun getCartItemsCount(): Single<CartItemCount>
}
package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.AddToCartResponse
import com.devsa.nikestore4.data.CartItemCount
import com.devsa.nikestore4.data.CartResponse
import com.devsa.nikestore4.data.MessageResponse
import com.devsa.nikestore4.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService):CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse>{
        return  apiService.addToCart(JsonObject().apply {
            addProperty("product_id",productId)
        })
    }
    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}
package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.AddToCartResponse
import com.devsa.nikestore4.data.CartItemCount
import com.devsa.nikestore4.data.CartResponse
import com.devsa.nikestore4.data.MessageResponse
import com.devsa.nikestore4.data.repo.source.CartDataSource
import io.reactivex.Single

class CartRepositoryImple(val cartDataSource: CartDataSource):CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> {
      return  cartDataSource.addToCart(productId)
    }

    override fun get(): Single<CartResponse> {
      return cartDataSource.get()
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        return  cartDataSource.remove(cartItemId)
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
       return  cartDataSource.changeCount(cartItemId,count)
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        return cartDataSource.getCartItemsCount()
    }
}
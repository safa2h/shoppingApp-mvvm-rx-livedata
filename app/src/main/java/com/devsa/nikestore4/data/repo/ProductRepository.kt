package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {
    fun getProducts(sort:Int):Single<List<Product>>
    fun getFavoritesProducts():Single<List<Product>>
    fun addToFAvorite(products:Product):Completable
    fun deleteFromFavorites(products:Product):Completable
}
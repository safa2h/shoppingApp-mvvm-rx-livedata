package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class RemoteDataSource(val apiService: ApiService) :ProductDataSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
      return  apiService.getProducts(sort.toString())
    }

    override fun getFavoritesProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFAvorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(): Completable {
        TODO("Not yet implemented")
    }
}
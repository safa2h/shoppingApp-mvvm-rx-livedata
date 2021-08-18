package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.data.repo.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImple(val remoteDataSource: ProductDataSource,val localDataSource: ProductDataSource):ProductRepository {
    override fun getProducts(sort:Int): Single<List<Product>> {
       return  remoteDataSource.getProducts(sort)
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
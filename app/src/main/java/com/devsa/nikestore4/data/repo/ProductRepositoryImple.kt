package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.data.repo.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImple(val remoteDataSource: ProductDataSource,val localDataSource: ProductDataSource):ProductRepository {
    override fun getProducts(sort:Int): Single<List<Product>> {
       return localDataSource.getFavoritesProducts().flatMap { favorites->
           remoteDataSource.getProducts(sort).doOnSuccess {
               val favIds=favorites.map {
                   it.id
               }
               it.forEach {product->
                   if (favIds.contains(product.id)){
                       product.isFavorite=true
                   }
               }
           }
       }
    }

    override fun getFavoritesProducts(): Single<List<Product>> {
      return  localDataSource.getFavoritesProducts()
    }

    override fun addToFAvorite(products:Product): Completable {
       return  localDataSource.addToFAvorite(products)
    }

    override fun deleteFromFavorites(products:Product): Completable {
       return  localDataSource.deleteFromFavorites(products)
    }
}
package com.devsa.nikestore4.data.repo.source

import androidx.room.*
import androidx.viewpager.widget.ViewPager
import com.devsa.nikestore4.data.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource:ProductDataSource {
    override fun getProducts(sort: Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavoritesProducts(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFAvorite(products: Product): Completable

    @Delete
    override fun deleteFromFavorites(products: Product): Completable

}
package com.devsa.nikestore4.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.data.repo.source.ProductLocalDataSource

@Database(entities = [Product::class],version = 1,exportSchema = false)
abstract class AppDb :RoomDatabase() {
    abstract  fun productDao (): ProductLocalDataSource

}
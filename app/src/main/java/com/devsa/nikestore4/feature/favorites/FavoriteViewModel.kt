package com.devsa.nikestore4.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.devsa.nikestore4.common.NikeCompletableObserver
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.data.SORTlATEST
import com.devsa.nikestore4.data.repo.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteViewModel(val productRepository: ProductRepository):NikeViewModel() {

    val prodcutLiveData= MutableLiveData<List<Product>>()

    init {
        productRepository.getFavoritesProducts()
            .subscribeOn(Schedulers.io())
            .subscribe(object :NikeSibgleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    prodcutLiveData.postValue(t)
                }
            })

    }



    fun removeFromFavorites(product:Product){
        productRepository.deleteFromFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Timber.i("deleted fav prodcut")
                }
            })
    }
}
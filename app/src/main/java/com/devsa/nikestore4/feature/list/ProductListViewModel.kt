package com.devsa.nikestore4.feature.list

import androidx.lifecycle.MutableLiveData
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeCompletableObserver
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.common.asyncNetworkRequest
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.data.repo.ProductRepository
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(var sort:Int, val productRepository: ProductRepository): NikeViewModel() {
    val productLiveDta= MutableLiveData<List<Product>>()
    val selectedLiveData=MutableLiveData<Int>()

    val sortTitle= arrayOf(R.string.sortlatest,R.string.sortpopular,R.string.sortPriceHightToLow, R.string.sortlPriceLowToHight)
    init {
        getProducts()
        selectedLiveData.value=sortTitle[sort]
    }

    fun getProducts(){
        progrssBarShowLiveData.value=true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progrssBarShowLiveData.value=false }
            .subscribe(object:NikeSibgleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                   productLiveDta.value=t
                }
            })
    }

    fun onSelectedSortByUser(sort: Int){
        this.sort=sort
        this.selectedLiveData.value=sortTitle[sort]
        getProducts()
    }

    fun addToFavorite(product: Product){
        if(product.isFavorite){
            productRepository.deleteFromFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object: NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite=false
                    }
                })
        }else{
            productRepository.addToFAvorite(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object: NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite=true
                    }
                })
        }

    }




}
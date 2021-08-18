package com.devsa.nikestore4.feature.main

import androidx.lifecycle.MutableLiveData
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.common.asyncNetworkRequest
import com.devsa.nikestore4.data.Banner
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.data.SORT_POPULAR
import com.devsa.nikestore4.data.SORTlATEST
import com.devsa.nikestore4.data.repo.BannerRepository
import com.devsa.nikestore4.data.repo.ProductRepository

class MainViewModel(private val productRepository: ProductRepository,val bannerRepository: BannerRepository):NikeViewModel() {
    val productLiveData=MutableLiveData<List<Product>>()
    val productPopularLiveData=MutableLiveData<List<Product>>()
    val bannertLiveData=MutableLiveData<List<Banner>>()

    init {
        progrssBarShowLiveData.value=true
        productRepository.getProducts(SORTlATEST)
            .asyncNetworkRequest()
            .doFinally { progrssBarShowLiveData.value=false }
            .subscribe(object :NikeSibgleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value=t
                }
            })
        productRepository.getProducts(SORT_POPULAR)
            .asyncNetworkRequest()
            .doFinally { progrssBarShowLiveData.value=false }
            .subscribe(object :NikeSibgleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productPopularLiveData.value=t
                }
            })

        bannerRepository.getBanners().asyncNetworkRequest()
            .subscribe(object :NikeSibgleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannertLiveData.value=t
                }
            })
    }

}
package com.devsa.nikestore4.feature.main

import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.data.CartItemCount
import com.devsa.nikestore4.data.TokenContainer
import com.devsa.nikestore4.data.repo.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainActivityViewModel(private  val cartRepository: CartRepository): NikeViewModel() {

    fun getCartItemCount(){
        if(!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemsCount().subscribeOn(Schedulers.io())
                .subscribe(object :NikeSibgleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }


                })
        }
    }


}
package com.devsa.nikestore4.feature.checkOut

import androidx.lifecycle.MutableLiveData
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.common.asyncNetworkRequest
import com.devsa.nikestore4.data.CheckOut
import com.devsa.nikestore4.data.repo.OrderRepository

class CheckOutViewModel(val orderRepository: OrderRepository, val orderId:Int): NikeViewModel() {
     val checkOutLiveDta=MutableLiveData<CheckOut>()

    init {
        orderRepository.checkOut(orderId)
            .asyncNetworkRequest()
            .subscribe(object :NikeSibgleObserver<CheckOut>(compositeDisposable){
                override fun onSuccess(t: CheckOut) {
                    checkOutLiveDta.value=t
                }
            })

    }
}
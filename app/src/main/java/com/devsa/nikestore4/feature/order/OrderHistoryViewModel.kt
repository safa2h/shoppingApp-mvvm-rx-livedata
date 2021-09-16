package com.devsa.nikestore4.feature.order

import androidx.lifecycle.MutableLiveData
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.common.asyncNetworkRequest
import com.devsa.nikestore4.data.OrderHistoryItem
import com.devsa.nikestore4.data.repo.OrderRepository

class OrderHistoryViewModel(orderRepository: OrderRepository) : NikeViewModel() {
    val orders = MutableLiveData<List<OrderHistoryItem>>()

    init {
        progrssBarShowLiveData.value = true
        orderRepository.list().asyncNetworkRequest().doFinally { progrssBarShowLiveData.value = false }
            .subscribe(object : NikeSibgleObserver<List<OrderHistoryItem>>(compositeDisposable){
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    orders.value=t
                }
            })
    }
}
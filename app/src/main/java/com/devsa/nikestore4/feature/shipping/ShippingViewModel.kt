package com.devsa.nikestore4.feature.shipping

import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.data.SubmitOrderResult
import com.devsa.nikestore4.data.repo.OrderRepository
import io.reactivex.Single


const val PAYMENT_METHOD_COD="cash_on_delivery"
const val PAYMENT_METHOD_ONLINE="online"

class ShippingViewModel(val orderRepository: OrderRepository):NikeViewModel() {


    fun submitOrder(
        firstName:String,
        lastName:String,
        postalCode:String,
        phoneNumber:String,
        address:String,
        payment_method:String)
            : Single<SubmitOrderResult> {
        return orderRepository.submit(firstName,lastName,postalCode,phoneNumber,address,payment_method)
    }
}
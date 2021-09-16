package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.CheckOut
import com.devsa.nikestore4.data.OrderHistoryItem
import com.devsa.nikestore4.data.SubmitOrderResult
import io.reactivex.Single

interface OrderDataSource {

    fun submit(firstName:String,lastName:String,postalCode:String,phoneNumber:String,address:String,paymentMethod:String): Single<SubmitOrderResult>

    fun checkOut(orderId:Int): Single<CheckOut>
    fun list():Single<List<OrderHistoryItem>>
}
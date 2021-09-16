package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.CheckOut
import com.devsa.nikestore4.data.OrderHistoryItem
import com.devsa.nikestore4.data.SubmitOrderResult
import com.devsa.nikestore4.data.repo.source.OrderDataSource
import io.reactivex.Single

class OrderRepositoryImple(val orderDataSource: OrderDataSource):OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
       return orderDataSource.submit(firstName,lastName,postalCode,phoneNumber,address,paymentMethod)
    }

    override fun checkOut(orderId: Int): Single<CheckOut> {
       return  orderDataSource.checkOut(orderId)
    }

    override fun list(): Single<List<OrderHistoryItem>> {
        return  orderDataSource.list()
    }
}
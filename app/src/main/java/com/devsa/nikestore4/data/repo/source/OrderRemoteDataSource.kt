package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.CheckOut
import com.devsa.nikestore4.data.OrderHistoryItem
import com.devsa.nikestore4.data.SubmitOrderResult
import com.devsa.nikestore4.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(val apiService: ApiService):OrderDataSource {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
       return  apiService.submitOrder(JsonObject().apply {
           addProperty("first_name",firstName)
           addProperty("last_name",lastName)
           addProperty("postal_code",postalCode)
           addProperty("mobile",phoneNumber)
           addProperty("address",address)
           addProperty("payment_method",paymentMethod)
       })
    }

    override fun checkOut(orderId: Int): Single<CheckOut> {
       return  apiService.orderCheckout(orderId)
    }

    override fun list(): Single<List<OrderHistoryItem>> = apiService.orders()
}
package com.devsa.nikestore4.services.http

import com.devsa.nikestore4.data.*
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("product/list")
    fun getProducts(@Query("sort") sort: String):Single<List<Product>>
    @GET("banner/slider")
    fun getBanners():Single<List<Banner>>
    @GET("comment/list")
    fun getComments(@Query("product_id") productId:Int):Single<List<Comment>>

    @POST("cart/changeCount")
    fun changeCount(@Body jsonObject: JsonObject):Single<AddToCartResponse>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject):Single<AddToCartResponse>

    @POST("cart/remove")
    fun removeItemFromCart(@Body jsonObject: JsonObject):Single<MessageResponse>

    @GET("cart/count")
    fun getCartItemCount():Single<CartItemCount>

    @GET("cart/list")
    fun getCart():Single<CartResponse>


}


fun creatApiServiceInstance():ApiService{

    val retrofit=Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)

}
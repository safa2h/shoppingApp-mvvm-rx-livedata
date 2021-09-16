package com.devsa.nikestore4.services.http

import com.devsa.nikestore4.data.*
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
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

    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject):Single<TokenResponse>
    @POST("user/register")
    fun signUp(@Body jsonObject: JsonObject):Single<MessageResponse>

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>

    @POST("order/submit")
    fun  submitOrder(@Body jsonObject: JsonObject):Single<SubmitOrderResult>

    @GET("order/checkout")
    fun  orderCheckout(@Query("order_id") orderId:Int ):Single<CheckOut>
    @GET("order/list")
    fun orders():Single<List<OrderHistoryItem>>



}


fun creatApiServiceInstance():ApiService{
    val okHttpClient= OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest=it.request()
            val newRequest=oldRequest.newBuilder()
            if(TokenContainer.token !=null)
                newRequest.addHeader("Authorization","Bearer ${TokenContainer.token}")
            newRequest.addHeader("Accept","application/json")
            newRequest.method(oldRequest.method, oldRequest.body)
            return@addInterceptor it.proceed(newRequest.build())

        }.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()


    val retrofit=Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)

}
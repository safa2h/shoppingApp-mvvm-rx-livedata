package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.MessageResponse
import com.devsa.nikestore4.data.TokenResponse
import com.devsa.nikestore4.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

const val   CLIENT_ID=2
const val   CLIENT_SECRET ="kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

class UserRemoteDataSourc(val apiService: ApiService):UserDataSource {
    override fun login(userName: String, password: String): Single<TokenResponse> {
       return  apiService.login(JsonObject().apply {
           addProperty("grant_type","password")
           addProperty("client_id", CLIENT_ID)
           addProperty("client_secret", CLIENT_SECRET)
           addProperty("username",userName)
           addProperty("password",password)
       })
    }

    override fun signUp(userName: String, password: String): Single<MessageResponse> {
        return  apiService.signUp(JsonObject().apply {
            addProperty("email",userName)
            addProperty("password",password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun getUserName(): String {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun saveUserName(userName: String) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}
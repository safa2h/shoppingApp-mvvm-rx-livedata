package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.MessageResponse
import com.devsa.nikestore4.data.TokenResponse
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {

    fun login(userName: String, password: String): Single<TokenResponse>

    fun signUp(userName: String, password: String): Single<MessageResponse>
    fun loadToken()
    fun getUserName():String
    fun saveToken(token:String,refreshToken:String)
    fun saveUserName(userName:String)

    fun signOut()
}
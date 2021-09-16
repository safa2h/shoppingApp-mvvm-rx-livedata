package com.devsa.nikestore4.data.repo

import io.reactivex.Completable

interface UserRepository {

    fun login(userName: String, password: String): Completable

    fun signUp(userName: String, password: String):Completable
    fun loadToken()
    fun getUserName():String

    fun signOut()

}
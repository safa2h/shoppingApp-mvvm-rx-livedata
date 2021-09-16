package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.MessageResponse
import com.devsa.nikestore4.data.TokenContainer
import com.devsa.nikestore4.data.TokenResponse
import com.devsa.nikestore4.data.repo.source.UserDataSource
import io.reactivex.Completable
import io.reactivex.Single

class UserRepositoryImple(val userRemoteDataSource:UserDataSource,val userlLocalDatasource:UserDataSource):UserRepository {
    override fun login(userName: String, password: String): Completable {
       return userRemoteDataSource.login(userName,password).doOnSuccess {
           onSuccessFullLogin(userName,it)
       }.ignoreElement()
    }

    override fun signUp(userName: String, password: String): Completable {
        return userRemoteDataSource.signUp(userName,password).flatMap {
            userRemoteDataSource.login(userName,password)
        }.doOnSuccess {
            onSuccessFullLogin(userName,it)
        }
            .ignoreElement()
    }

    override fun loadToken() {
        userlLocalDatasource.loadToken()
    }

    override fun getUserName(): String {
       return  userlLocalDatasource.getUserName()
    }

    override fun signOut() {
       userlLocalDatasource.signOut()
        TokenContainer.update(null,null)
    }

    fun onSuccessFullLogin(userName:String,tokeResponse:TokenResponse){
        TokenContainer.update(tokeResponse.access_token,tokeResponse.refresh_token)
        userlLocalDatasource.saveToken(tokeResponse.access_token,tokeResponse.refresh_token)
        userlLocalDatasource.saveUserName(userName)
    }
}
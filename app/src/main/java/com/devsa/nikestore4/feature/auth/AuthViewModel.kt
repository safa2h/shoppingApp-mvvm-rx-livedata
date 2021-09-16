package com.devsa.nikestore4.feature.auth

import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.data.repo.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository):NikeViewModel() {

    fun login(userName:String,password:String):Completable{
        return  userRepository.login(userName,password)
    }

    fun signUp(userName:String,password:String):Completable{
        return  userRepository.signUp(userName,password)
    }


}
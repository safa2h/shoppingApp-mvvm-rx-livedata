package com.devsa.nikestore4.feature.profile

import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.data.TokenContainer
import com.devsa.nikestore4.data.repo.UserRepository

class ProfileViewmodel(val userRepository: UserRepository):NikeViewModel() {

    val userName:String
        get() =userRepository.getUserName()
    val isSIginedIn: Boolean
        get() = TokenContainer.token != null

    fun siginOut() = userRepository.signOut()


}
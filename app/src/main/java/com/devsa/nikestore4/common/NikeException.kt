package com.devsa.nikestore4.common

import androidx.annotation.StringRes

class NikeException(val type:Type,@StringRes val userFreindlyMessgae:Int=0,val serverMessgae:String?=null):Throwable() {



    enum class Type{
        SIMPLE,DIALOG,AUTH
    }
}
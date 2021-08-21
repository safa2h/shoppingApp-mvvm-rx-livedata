package com.devsa.nikestore4.common

import com.devsa.nikestore4.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

class NikeExceptiomMapper {

    companion object{
        fun map(throwable: Throwable):NikeException{
            if (throwable is HttpException) {
                try {

                    val errorJsonObjec = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage = errorJsonObjec.getString("message")
                    return NikeException(
                        if (throwable.code() == 401) NikeException.Type.AUTH else NikeException.Type.SIMPLE,
                        serverMessgae = errorMessage
                    )
                } catch (expetion: Exception) {
                    Timber.i(expetion)
                }
            }
            return NikeException(NikeException.Type.SIMPLE, userFreindlyMessgae = R.string.unknownError)
        }
    }
}
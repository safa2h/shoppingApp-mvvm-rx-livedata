package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.Comment
import io.reactivex.Single

interface CommentRepository {

    fun get(productId:Int):Single<List<Comment>>
}
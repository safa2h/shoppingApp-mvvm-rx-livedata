package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun get(productId:Int): Single<List<Comment>>
}
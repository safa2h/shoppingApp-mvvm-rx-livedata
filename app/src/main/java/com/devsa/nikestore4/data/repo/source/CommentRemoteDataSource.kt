package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.Comment
import com.devsa.nikestore4.services.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService):CommentDataSource {
    override fun get(productId: Int): Single<List<Comment>> {
       return  apiService.getComments(productId)
    }
}
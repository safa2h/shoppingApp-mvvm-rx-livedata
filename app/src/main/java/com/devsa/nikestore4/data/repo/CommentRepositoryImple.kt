package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.Comment
import com.devsa.nikestore4.data.repo.CommentRepository
import com.devsa.nikestore4.data.repo.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImple(val commentDataSource: CommentDataSource):CommentRepository {
    override fun get(productId: Int): Single<List<Comment>> {
        return commentDataSource.get(productId)
    }
}
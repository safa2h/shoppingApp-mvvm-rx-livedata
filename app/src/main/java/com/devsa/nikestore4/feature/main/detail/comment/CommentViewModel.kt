package com.devsa.nikestore4.feature.main.detail.comment

import androidx.lifecycle.MutableLiveData
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.common.asyncNetworkRequest
import com.devsa.nikestore4.data.Comment
import com.devsa.nikestore4.data.repo.CommentRepository

class CommentViewModel(val productId:Int,commentRepository: CommentRepository):NikeViewModel() {
    val commentLiveData=MutableLiveData<List<Comment>>()

    init {
        progrssBarShowLiveData.value=true
        commentRepository.get(productId)
            .asyncNetworkRequest()
            .doFinally { progrssBarShowLiveData.value=false }
            .subscribe(object :NikeSibgleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value=t
                }
            })
    }
}
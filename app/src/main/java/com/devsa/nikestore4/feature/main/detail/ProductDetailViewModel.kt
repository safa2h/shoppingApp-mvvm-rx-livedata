package com.devsa.nikestore4.feature.main.detail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.devsa.nikestore4.common.EXTRA_KEy
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.common.asyncNetworkRequest
import com.devsa.nikestore4.data.Comment
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.data.repo.CommentRepository
import java.util.ArrayList

class ProductDetailViewModel(bundle: Bundle,commentRepository: CommentRepository):NikeViewModel() {
    val productDetailLiveData=MutableLiveData<Product>()
    val commentDetailLiveData=MutableLiveData<List<Comment>>()

    init {
        progrssBarShowLiveData.value=true
        productDetailLiveData.value=bundle.getParcelable(EXTRA_KEy)
        commentRepository.get(productDetailLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally {progrssBarShowLiveData.value=false  }
            .subscribe(object:NikeSibgleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentDetailLiveData.value=t
                }
            })

    }


}
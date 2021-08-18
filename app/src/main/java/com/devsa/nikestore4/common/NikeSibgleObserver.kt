package com.devsa.nikestore4.common

import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class NikeSibgleObserver<T>(val compositeDisposable: CompositeDisposable):SingleObserver<T> {
     override fun onError(e: Throwable) {
       Timber.e(e)
     }

    override fun onSubscribe(d: Disposable) {
      compositeDisposable.add(d)
    }

}
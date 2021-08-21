package com.devsa.nikestore4.common

import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

abstract class NikeCompletableObserver(val compositeDisposable: CompositeDisposable):CompletableObserver {

    override fun onSubscribe(d: Disposable) {
       compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
     Timber.i(e)
        EventBus.getDefault().post(NikeExceptiomMapper.map(e))

    }
}
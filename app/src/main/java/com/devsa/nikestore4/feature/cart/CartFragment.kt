package com.devsa.nikestore4.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeFragment

class CartFragment:NikeFragment() {
    override fun setProgressIndicator(mustShow: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cart_fragment,container,false)
    }
}
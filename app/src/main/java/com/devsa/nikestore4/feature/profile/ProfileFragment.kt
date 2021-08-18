package com.devsa.nikestore4.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeFragment

class ProfileFragment:NikeFragment() {
    override fun setProgressIndicator(mustShow: Boolean) {
        TODO("Not yet implemented")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment,container,false)
    }

}
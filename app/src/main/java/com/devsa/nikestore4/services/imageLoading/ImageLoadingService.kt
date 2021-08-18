package com.devsa.nikestore4.services.imageLoading

import com.devsa.nikestore4.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView:NikeImageView,imageUrl:String)
}
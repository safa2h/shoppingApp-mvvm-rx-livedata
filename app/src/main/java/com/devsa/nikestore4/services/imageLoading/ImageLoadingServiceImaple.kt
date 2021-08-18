package com.devsa.nikestore4.services.imageLoading

import com.devsa.nikestore4.view.NikeImageView
import java.lang.IllegalStateException

class ImageLoadingServiceImaple:ImageLoadingService {
    override fun load(imageView: NikeImageView, imageUrl: String) {
        if (imageView is NikeImageView){
            imageView.setImageURI(imageUrl)
        }else
            throw IllegalStateException("imageView Must be SimpleDrawView")
    }
}
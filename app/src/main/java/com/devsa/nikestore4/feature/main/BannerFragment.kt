package com.devsa.nikestore4.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEy
import com.devsa.nikestore4.data.Banner
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import com.devsa.nikestore4.view.NikeImageView
import org.koin.android.ext.android.inject
import java.lang.IllegalStateException

class BannerFragment:Fragment() {
    val ImageLoadingService:ImageLoadingService by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageView=inflater.inflate(R.layout.frgment_banner,container,false) as NikeImageView
        val banner=requireArguments().getParcelable<Banner>(EXTRA_KEy)?:throw IllegalStateException("banner can not be null")
        ImageLoadingService.load(imageView,banner.image)

        return imageView
    }

   companion object{
       fun newInstance(banner:Banner):BannerFragment  {

           return BannerFragment().apply {
               arguments=Bundle().apply {
                   putParcelable(EXTRA_KEy,banner)
               }

           }


       }
   }
}
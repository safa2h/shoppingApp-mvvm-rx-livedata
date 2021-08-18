package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners(): Single<List<Banner>>

}
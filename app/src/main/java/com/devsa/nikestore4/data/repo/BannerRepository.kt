package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.Banner
import io.reactivex.Single

interface BannerRepository {

    fun getBanners():Single<List<Banner>>
}
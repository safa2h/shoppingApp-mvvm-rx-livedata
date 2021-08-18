package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.Banner
import com.devsa.nikestore4.data.repo.BannerRepository
import io.reactivex.Single

class BannerRepositoryImple:BannerRepository {
    override fun getBanners(): Single<List<Banner>> {
        TODO("Not yet implemented")
    }
}
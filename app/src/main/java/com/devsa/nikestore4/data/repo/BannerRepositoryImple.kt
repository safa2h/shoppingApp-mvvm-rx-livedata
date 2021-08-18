package com.devsa.nikestore4.data.repo

import com.devsa.nikestore4.data.Banner
import com.devsa.nikestore4.data.repo.source.BannerDataSource
import io.reactivex.Single

class BannerRepositoryImple(val bannerDataSource: BannerDataSource):BannerRepository {
    override fun getBanners(): Single<List<Banner>> {
       return bannerDataSource.getBanners()
    }
}
package com.devsa.nikestore4.data.repo.source

import com.devsa.nikestore4.data.Banner
import com.devsa.nikestore4.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService):BannerDataSource {
    override fun getBanners(): Single<List<Banner>> {
     return  apiService.getBanners()
    }
}
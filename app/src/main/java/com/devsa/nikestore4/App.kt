package com.devsa.nikestore4

import android.app.Application
import android.os.Bundle
import com.devsa.nikestore4.data.repo.*
import com.devsa.nikestore4.data.repo.source.BannerRemoteDataSource
import com.devsa.nikestore4.data.repo.source.CommentRemoteDataSource
import com.devsa.nikestore4.data.repo.source.LocalDataSource
import com.devsa.nikestore4.data.repo.source.RemoteDataSource
import com.devsa.nikestore4.feature.list.ProductListViewModel
import com.devsa.nikestore4.feature.main.MainViewModel
import com.devsa.nikestore4.feature.main.ProdudtAdapter
import com.devsa.nikestore4.feature.main.detail.CommentAdapter
import com.devsa.nikestore4.feature.main.detail.ProductDetailViewModel
import com.devsa.nikestore4.feature.main.detail.comment.CommentViewModel
import com.devsa.nikestore4.services.http.creatApiServiceInstance
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import com.devsa.nikestore4.services.imageLoading.ImageLoadingServiceImaple
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant (Timber.DebugTree())
        Fresco.initialize(this)

        val myModuals = module {

            single { creatApiServiceInstance() }
            single<ImageLoadingService> {ImageLoadingServiceImaple()  }
            factory<ProductRepository> {ProductRepositoryImple(RemoteDataSource(get()),LocalDataSource())  }
            factory<BannerRepository> {BannerRepositoryImple(BannerRemoteDataSource(get()))  }
            factory<CommentRepository> {CommentRepositoryImple(CommentRemoteDataSource(get()))  }
            factory {(viewType:Int)-> ProdudtAdapter(viewType,get()) }
            factory { (mustShow:Boolean)->CommentAdapter(mustShow) }
            viewModel{MainViewModel(get(),get())}
            viewModel { (bundle:Bundle)->ProductDetailViewModel(bundle,get()) }
            viewModel {(productId:Int)-> CommentViewModel(productId,get()) }
            viewModel {(sort:Int)->ProductListViewModel(sort,get()) }
        }
        startKoin {
            androidContext(this@App)
            modules(myModuals)

        }

    }

}
package com.devsa.nikestore4

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.devsa.nikestore4.data.db.AppDb
import com.devsa.nikestore4.data.repo.*
import com.devsa.nikestore4.data.repo.BannerRepositoryImple
import com.devsa.nikestore4.data.repo.source.*
import com.devsa.nikestore4.feature.auth.AuthViewModel
import com.devsa.nikestore4.feature.cart.CartViewModel
import com.devsa.nikestore4.feature.checkOut.CheckOutViewModel
import com.devsa.nikestore4.feature.favorites.FavoriteViewModel
import com.devsa.nikestore4.feature.list.ProductListViewModel
import com.devsa.nikestore4.feature.main.MainActivityViewModel
import com.devsa.nikestore4.feature.main.MainViewModel
import com.devsa.nikestore4.feature.main.ProdudtAdapter
import com.devsa.nikestore4.feature.main.detail.CommentAdapter
import com.devsa.nikestore4.feature.main.detail.ProductDetailViewModel
import com.devsa.nikestore4.feature.main.detail.comment.CommentViewModel
import com.devsa.nikestore4.feature.order.OrderHistoryViewModel
import com.devsa.nikestore4.feature.profile.ProfileViewmodel
import com.devsa.nikestore4.feature.shipping.ShippingViewModel
import com.devsa.nikestore4.services.http.creatApiServiceInstance
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import com.devsa.nikestore4.services.imageLoading.ImageLoadingServiceImaple
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)

        val myModuals = module {

            single { Room.databaseBuilder(this@App, AppDb::class.java, "db_app").build() }
            single { creatApiServiceInstance() }
            single<ImageLoadingService> { ImageLoadingServiceImaple() }
            single<SharedPreferences> { this@App.getSharedPreferences("app_shard", MODE_PRIVATE) }
            single<UserRepository> {
                UserRepositoryImple(
                    UserRemoteDataSourc(get()),
                    UserLocalDataSouce(get())
                )
            }
            single { UserLocalDataSouce(get()) }
            single<OrderRepository> { OrderRepositoryImple(OrderRemoteDataSource(get())) }


            factory<ProductRepository> {
                ProductRepositoryImple(
                    ProductRemoteDataSource(get()),
                    get<AppDb>().productDao()
                )
            }
            factory<BannerRepository> { BannerRepositoryImple(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImple(CommentRemoteDataSource(get())) }
            factory { (viewType: Int) -> ProdudtAdapter(viewType, get()) }
            factory { (mustShow: Boolean) -> CommentAdapter(mustShow) }
            factory<CartRepository> { CartRepositoryImple(CartRemoteDataSource(get())) }


            viewModel { MainViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainActivityViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { ProfileViewmodel(get()) }
            viewModel { (orderId: Int) -> CheckOutViewModel(get(), orderId) }
            viewModel { OrderHistoryViewModel(get())}
            viewModel { FavoriteViewModel(get()) }


        }
        startKoin {
            androidContext(this@App)
            modules(myModuals)

        }

        val userRepository: UserRepository = get()
        userRepository.loadToken()

    }

}
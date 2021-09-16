package com.devsa.nikestore4.feature.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEy
import com.devsa.nikestore4.common.NikeActivitiy
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.feature.main.detail.ProductDetailActivity
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritActivity : NikeActivitiy() ,FavoriteAdapter.FavoriteProductEventListener{
    val favoriteViewModel: FavoriteViewModel by viewModel()
    val imageLoadingService: ImageLoadingService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorit)

        val rv_fav = findViewById<RecyclerView>(R.id.rv_favorites)
        rv_fav.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        favoriteViewModel.prodcutLiveData.observe(this, {
           if(it.isNotEmpty()){
               val favoriteAdapter=FavoriteAdapter(it as MutableList<Product>,imageLoadingService,this)
               rv_fav.adapter=favoriteAdapter
           }else{
               showEmptyState(R.layout.view_cart_empty_state)
           }
        })

    }

    override fun productOnClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEy,product)
        })
    }

    override fun productOnLongClick(product: Product) {
        favoriteViewModel.removeFromFavorites(product)
    }
}
package com.devsa.nikestore4.feature.main.detail

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEY_ID
import com.devsa.nikestore4.common.NikeActivitiy
import com.devsa.nikestore4.common.formatPrice
import com.devsa.nikestore4.data.Comment
import com.devsa.nikestore4.feature.main.detail.comment.CommentActivity
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import com.devsa.nikestore4.view.NikeImageView
import com.devsa.nikestore4.view.scroll.ObservableScrollView
import com.devsa.nikestore4.view.scroll.ObservableScrollViewCallbacks
import com.devsa.nikestore4.view.scroll.ScrollState
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailActivity : NikeActivitiy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        val productDetailViewModel:ProductDetailViewModel by viewModel{ parametersOf(intent.extras)}
        val imageLoadingService:ImageLoadingService by inject()
        val commentAdapter:CommentAdapter by inject{parametersOf(false)}


        val product_iv: NikeImageView = findViewById(R.id.product_detail_iv)
        val titleTv: TextView = findViewById(R.id.product_detail_title)
        val toolbarTitle: TextView = findViewById(R.id.toolbar_title_detail)
        val viewAllComments: Button = findViewById(R.id.viewAllcomments)
        val prev_price: TextView = findViewById(R.id.previous_detai_price)
        val currnet_price: TextView = findViewById(R.id.current_detail_price)
        val toolbarView_detail: CardView = findViewById(R.id.toolbarView_detail)
        val rv_detail_comment: RecyclerView = findViewById(R.id.rv_detail_comment)
        val addToCartBtn: ExtendedFloatingActionButton = findViewById(R.id.addToCartBtn)
        val observableScrollView: ObservableScrollView = findViewById(R.id.observableScrollView)

        rv_detail_comment.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        productDetailViewModel.productDetailLiveData.observe(this,{
            imageLoadingService.load(product_iv, it.image)
            titleTv.text = it.title
            toolbarTitle.text = it.title
            prev_price.text = formatPrice(it.previous_price)
            prev_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currnet_price.text = formatPrice(it.price)
        })
        productDetailViewModel.commentDetailLiveData.observe(this,{
            Timber.i(it.toString())
            commentAdapter.comments= it as ArrayList<Comment>
            rv_detail_comment.adapter=commentAdapter

            if(it.size>3){
                viewAllComments.visibility= View.VISIBLE
                viewAllComments.setOnClickListener {
                    startActivity(Intent(this,CommentActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID,productDetailViewModel.productDetailLiveData.value!!.id)
                    })
                }

            }
        })

        productDetailViewModel.progrssBarShowLiveData.observe(this,{
            setProgressIndicator(it)
        })

        product_iv.post {
            val imageViewHeight = product_iv.height
            val prodcutImageView = product_iv
            val toolbarView = toolbarView_detail

            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    toolbarView.alpha = scrollY.toFloat() / imageViewHeight.toFloat()
                    prodcutImageView.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }


            })
        }

    }
}
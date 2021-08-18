package com.devsa.nikestore4.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEy
import com.devsa.nikestore4.common.NikeFragment
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.convertDpToPixel
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.data.SORT_POPULAR
import com.devsa.nikestore4.data.SORTlATEST
import com.devsa.nikestore4.feature.list.ProductListActivity
import com.devsa.nikestore4.feature.main.detail.ProductDetailActivity
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.ArrayList

class MainFrgment:NikeFragment(),ProdudtAdapter.OnProductListener {

    val mainViewModel:MainViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btn_lastest: Button =view.findViewById(R.id.main_viewAllLastBtn)
        val btn_popular: Button =view.findViewById(R.id.main_viewAllPopularBtn)

        btn_lastest.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEy, SORTlATEST)
            })
        }
        btn_popular.setOnClickListener {
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEy, SORT_POPULAR)
            })
        }



        mainViewModel.productLiveData.observe(viewLifecycleOwner,{
            val produdtAdapter:ProdudtAdapter by inject { parametersOf(VIEW_TYPE_ROUND)}
            produdtAdapter.products= it as ArrayList<Product>
            val rv_main_product_new=view.findViewById<RecyclerView>(R.id.productRv)
            rv_main_product_new.layoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            rv_main_product_new.adapter=produdtAdapter
            produdtAdapter.productListener=this
        })
        mainViewModel.productPopularLiveData.observe(viewLifecycleOwner,{
            val produdtAdapter:ProdudtAdapter by inject {parametersOf(VIEW_TYPE_ROUND)}
            produdtAdapter.products= it as ArrayList<Product>
            val rv_main_product_popular=view.findViewById<RecyclerView>(R.id.productRvMostSeller)
            rv_main_product_popular.layoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            rv_main_product_popular.adapter=produdtAdapter
            produdtAdapter.productListener=this

        })
        mainViewModel.progrssBarShowLiveData.observe(viewLifecycleOwner,{
            setProgressIndicator(it)
        })
        mainViewModel.bannertLiveData.observe(viewLifecycleOwner,{
            val viewPager_main=view.findViewById<ViewPager2>(R.id.viewPager_main)
            val dots_indicator=view.findViewById<DotsIndicator>(R.id.dots_indicator)
            val bannerSliderAdapter = BannerAdapter(this,it)
            val viewPagerHeight = ((viewPager_main.measuredWidth - convertDpToPixel(
                32f,
                requireContext()
            )) * 173) / 328
            val layputParams = viewPager_main.layoutParams
            layputParams.height = viewPagerHeight.toInt()
            viewPager_main.adapter =bannerSliderAdapter
            dots_indicator.setViewPager2(viewPager_main)
            Timber.i(it.toString())
        })


    }

    override fun onProductClickListener(product: Product) {
       startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
           putExtra(EXTRA_KEy,product)
       })
    }

}
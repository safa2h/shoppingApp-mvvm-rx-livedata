package com.devsa.nikestore4.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devsa.nikestore4.data.Banner

class BannerAdapter(fragment: Fragment,val banners:List<Banner>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
       return  banners.size
    }

    override fun createFragment(position: Int): Fragment {
      return  BannerFragment.newInstance(banners[position])
    }
}
package com.devsa.nikestore4.feature.favorites

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import com.devsa.nikestore4.view.NikeImageView

class FavoriteAdapter(val products: MutableList<Product>, val iimageLoadingService: ImageLoadingService, val favoriteProductEventListener: FavoriteProductEventListener) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTv = itemView.findViewById<TextView>(R.id.fav_item_tv)
        val fav_img = itemView.findViewById<NikeImageView>(R.id.fav_img)
        fun bindProduct(product: Product) {
            titleTv.text = product.title
            iimageLoadingService.load(fav_img,product.image)
            itemView.setOnClickListener {
                favoriteProductEventListener.productOnClick(product)
            }

            itemView.setOnLongClickListener {
                favoriteProductEventListener.productOnLongClick(product)
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int {
        return  products.size
    }

    interface  FavoriteProductEventListener{
        fun productOnClick(product: Product)
        fun productOnLongClick(product: Product)
    }
}
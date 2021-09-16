package com.devsa.nikestore4.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.formatPrice
import com.devsa.nikestore4.common.implementSpringAnimationTrait
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import com.devsa.nikestore4.view.NikeImageView
import java.lang.IllegalStateException
import java.util.ArrayList

const val VIEW_TYPE_ROUND=0
const val VIEW_TYPE_SMALL=1
const val VIEW_TYPE_LARGE=2

class ProdudtAdapter(var viewType:Int= VIEW_TYPE_ROUND, val imageLoadingService: ImageLoadingService): RecyclerView.Adapter<ProdudtAdapter.ProductViewholder>() {

    var productListener:OnProductListener?=null

    var products=ArrayList<Product>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewholder {
        val res_id=when(viewType) {
            VIEW_TYPE_ROUND->R.layout.item_product
            VIEW_TYPE_SMALL->R.layout.item_product_small
            VIEW_TYPE_LARGE->R.layout.item_product_large
           else-> throw  IllegalStateException ("redundent")
        }
            return  ProductViewholder(LayoutInflater.from(parent.context).inflate(res_id,parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewholder, position: Int) {
       holder.onbind(products[position])
    }

    override fun getItemCount(): Int {
       return products.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }


    inner class  ProductViewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.productTitleTv)
        var currnetPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
        var previousPriceTv: TextView = itemView.findViewById(R.id.previousPriceTv)
        val productIv: NikeImageView = itemView.findViewById(R.id.productIv)
        val favoriteBtnIv: ImageView = itemView.findViewById(R.id.favoriteBtn)

        fun onbind(product: Product){
            imageLoadingService.load(productIv, product.image)
            titleTv.text = product.title
            currnetPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.setOnClickListener {
                productListener?.onProductClickListener(product)
            }
            if(product.isFavorite){
                favoriteBtnIv.setImageResource(R.drawable.ic_baseline_favorite_fill_24)
            }else
                favoriteBtnIv.setImageResource(R.drawable.ic_favorites)

            favoriteBtnIv.setOnClickListener {
                productListener?.onFavoriteClickListener(product)
                product.isFavorite=!product.isFavorite
                notifyItemChanged(adapterPosition)
            }


            itemView.implementSpringAnimationTrait()
        }
    }

    interface  OnProductListener{
        fun onProductClickListener(product: Product)
        fun onFavoriteClickListener(product: Product)
    }
}
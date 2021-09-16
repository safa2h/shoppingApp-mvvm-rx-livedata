package com.devsa.nikestore4.feature.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.formatPrice
import com.devsa.nikestore4.data.CartItem
import com.devsa.nikestore4.data.PurchaseDetail
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import com.devsa.nikestore4.view.NikeImageView


const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAIL = 1

class CartAdapter(
    val imageLoadingService: ImageLoadingService,
    val cartItemViewCallBack: CartItemViewCallBack,
    val cartItems: MutableList<CartItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var purchaseDetail: PurchaseDetail?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType== VIEW_TYPE_CART_ITEM){
            CartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false))
        }else {
            PurchaseDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_details,parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CartViewHolder){
            holder.onBindCart(cartItems[position])
        }else if (holder is PurchaseDetailViewHolder) {
            purchaseDetail?.let {
                holder.bind(it.total_price,it.shipping_cost,it.payable_price)
            }
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (cartItems.size == position)
            return VIEW_TYPE_PURCHASE_DETAIL
        else
            return VIEW_TYPE_CART_ITEM
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productTitleTv_item_cart =
            itemView.findViewById<TextView>(R.id.productTitleTv_item_cart)
        val cartItemCountTv_cart_item =
            itemView.findViewById<TextView>(R.id.cartItemCountTv_cart_item)
        val previousPriceTv_item_cart =
            itemView.findViewById<TextView>(R.id.previousPriceTv_item_cart)
        val removeFromCartBtn = itemView.findViewById<TextView>(R.id.removeFromCartBtn)
        val currentPriceTv_item_cart =
            itemView.findViewById<TextView>(R.id.currentPriceTv_item_cart)
        val productIv_item_cart = itemView.findViewById<NikeImageView>(R.id.productIv_item_cart)
        val increaseBtn = itemView.findViewById<ImageView>(R.id.increaseBtn)
        val decreaseBtn = itemView.findViewById<ImageView>(R.id.decreaseBtn)
        val changeCountPrg = itemView.findViewById<ProgressBar>(R.id.changeCountPrg)

        fun onBindCart(cartItem: CartItem) {
            productTitleTv_item_cart.text = cartItem.product.title
            cartItemCountTv_cart_item.text = cartItem.count.toString()
            previousPriceTv_item_cart.text =
                formatPrice(cartItem.product.price + cartItem.product.discount)
            currentPriceTv_item_cart.text = formatPrice(cartItem.product.price)
            imageLoadingService.load(productIv_item_cart, cartItem.product.image)

            removeFromCartBtn.setOnClickListener {
                cartItemViewCallBack.onRemoveItem(cartItem)
            }
            increaseBtn.setOnClickListener {
                cartItem.changeCountProgressBarIsVisible = true
                changeCountPrg.visibility = View.VISIBLE
                cartItemCountTv_cart_item.visibility = View.INVISIBLE
                cartItemViewCallBack.onIncreaseCartItem(cartItem)
            }
            decreaseBtn.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarIsVisible = true
                    changeCountPrg.visibility = View.VISIBLE
                    cartItemCountTv_cart_item.visibility = View.INVISIBLE
                    cartItemViewCallBack.onDecreaseCartItem(cartItem)
                }
            }

            changeCountPrg.visibility =
                (if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.INVISIBLE)
            cartItemCountTv_cart_item.visibility =
                (if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE)

            currentPriceTv_item_cart.setOnClickListener {
                cartItemViewCallBack.onProductImageClick(cartItem)
            }
            if (cartItem.changeCountProgressBarIsVisible) {
                changeCountPrg.visibility = View.VISIBLE
                cartItemCountTv_cart_item.visibility = View.INVISIBLE
            }


        }

    }

    class PurchaseDetailViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val totalPriceTv_item_purchase =
            itemView.findViewById<TextView>(R.id.totalPriceTv_item_purchase)
        val shipping_costTv_item_purchase =
            itemView.findViewById<TextView>(R.id.shipping_costTv_item_purchase)
        val payable_priceTv_item_purchaase =
            itemView.findViewById<TextView>(R.id.payable_priceTv_item_purchaase)

        fun bind(totalPrice: Int, shippingCost: Int, payablePrice: Int) {
            totalPriceTv_item_purchase.text = formatPrice(totalPrice)
            shipping_costTv_item_purchase.text = formatPrice(shippingCost)
            payable_priceTv_item_purchaase.text = formatPrice(payablePrice)

        }


    }


    interface CartItemViewCallBack {
        fun onRemoveItem(cartItem: CartItem)
        fun onIncreaseCartItem(cartItem: CartItem)
        fun onDecreaseCartItem(cartItem: CartItem)
        fun onProductImageClick(cartItem: CartItem)

    }

    fun removeCartItem(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun incresaseCountCartItem(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    fun decreseCountCartItem(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }


}
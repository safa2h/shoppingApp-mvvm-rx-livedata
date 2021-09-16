package com.sevenlearn.nikestore.feature.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.convertDpToPixel
import com.devsa.nikestore4.common.formatPrice
import com.devsa.nikestore4.data.OrderHistoryItem
import com.devsa.nikestore4.view.NikeImageView
import kotlinx.android.extensions.LayoutContainer

class OrderHistoryItemAdapter(val context: Context, val orderHistoryItems: List<OrderHistoryItem>) :
    RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder>() {
    val layoutParams: LinearLayout.LayoutParams

    init {
        val size = convertDpToPixel(100f, context).toInt()
        val margin = convertDpToPixel(8f, context).toInt()
        layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        val orderIdTv=containerView.findViewById<TextView>(R.id.orderIdTv)
        val orderAmountTv=containerView.findViewById<TextView>(R.id.orderAmountTv)
        val orderProductsLl=containerView.findViewById<LinearLayout>(R.id.orderProductsLl)

        fun bind(orderHistoryItem: OrderHistoryItem) {
           orderIdTv.text = orderHistoryItem.id.toString()
           orderAmountTv.text = formatPrice(orderHistoryItem.payable)
           orderProductsLl.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView = NikeImageView(context)
                imageView.layoutParams = layoutParams
                imageView.setImageURI(it.product.image)
               orderProductsLl.addView(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_order_history, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(orderHistoryItems[position])

    override fun getItemCount(): Int = orderHistoryItems.size
}
package com.devsa.nikestore4.feature.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeActivitiy
import com.sevenlearn.nikestore.feature.order.OrderHistoryItemAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class OrderHistoryActivity : NikeActivitiy() {
    val viewModel: OrderHistoryViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        val orderHistoryRv=findViewById<RecyclerView>(R.id.orderHistoryRv)

        viewModel.progrssBarShowLiveData.observe(this) {

            setProgressIndicator(it)
        }

        orderHistoryRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.orders.observe(this) {
            orderHistoryRv.adapter = OrderHistoryItemAdapter(this, it)
        }


    }
}
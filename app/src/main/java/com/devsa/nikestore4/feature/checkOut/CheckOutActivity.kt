package com.devsa.nikestore4.feature.checkOut

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEY_ID
import com.devsa.nikestore4.common.formatPrice
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        val checkOutViewModel:CheckOutViewModel by viewModel{
            val uri: Uri?=intent.data

            if (uri!=null)
                parametersOf(uri.getQueryParameter("order_id")!!.toInt())
            else
                parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))

        }

        val orderStatusTv=findViewById<TextView>(R.id.orderStatusTv)
        val orderPriceTv=findViewById<TextView>(R.id.orderPriceTv)
        val purchaseStatusTv=findViewById<TextView>(R.id.purchaseStatusTv)

        checkOutViewModel.checkOutLiveDta.observe(this,{
            orderPriceTv.text= formatPrice(it.payable_price)
            orderStatusTv.text=it.payment_status
            purchaseStatusTv.text=if(it.purchase_success) "خرید شما با موفقیت انجام شد" else "خرید ناموفق"
        })
    }
}
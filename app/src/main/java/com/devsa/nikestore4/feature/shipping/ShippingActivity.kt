package com.devsa.nikestore4.feature.shipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEY_ID
import com.devsa.nikestore4.common.EXTRA_KEy
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.openUrlInCustomTab
import com.devsa.nikestore4.data.PurchaseDetail
import com.devsa.nikestore4.data.SubmitOrderResult
import com.devsa.nikestore4.feature.cart.CartAdapter
import com.devsa.nikestore4.feature.checkOut.CheckOutActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException

class ShippingActivity : AppCompatActivity() {

    val shippingViewModel:ShippingViewModel by viewModel()

    val compositeDisposable= CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)




        val first_name=findViewById<EditText>(R.id.firstNameEt_Shipping)
        val last_name=findViewById<EditText>(R.id.lastNameEt_Shipping)
        val postal_code=findViewById<EditText>(R.id.postalCodeEt_Shipping)
        val phone_number=findViewById<EditText>(R.id.mobileEt_Shipping)
        val address=findViewById<EditText>(R.id.addressEt_Shipping)
        val codBtn=findViewById<Button>(R.id.codBtn)
        val onilePayBtn=findViewById<Button>(R.id.onilePayBtn)


        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEy)
            ?: throw  IllegalStateException("purchase detail shouldnt be null")

        val item_purchase_details = findViewById<View>(R.id.purchaseDetailVie_shipping)

        val purchaseDetailViewHolder = CartAdapter.PurchaseDetailViewHolder(item_purchase_details)
        purchaseDetailViewHolder.bind(purchaseDetail.total_price,purchaseDetail.shipping_cost,purchaseDetail.payable_price)


        val onclickListener=View.OnClickListener {
            shippingViewModel.submitOrder(
                first_name.text.toString()
                ,last_name.text.toString()
                ,postal_code.text.toString()
                ,phone_number.text.toString()
                ,address.text.toString()
                ,if(it.id==R.id.onilePayBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object:NikeSibgleObserver<SubmitOrderResult>(compositeDisposable){
                    override fun onSuccess(t: SubmitOrderResult) {
                        if (t.bank_geteway_url.isNotEmpty()){
                            openUrlInCustomTab(this@ShippingActivity,t.bank_geteway_url)
                        }else{
                            startActivity(Intent(this@ShippingActivity, CheckOutActivity::class.java).apply {
                                putExtra(EXTRA_KEY_ID,t.order_id)
                            })
                        }
                        finish()
                    }
                })
        }

        codBtn.setOnClickListener { onclickListener }
        onilePayBtn.setOnClickListener { onclickListener }



    }
}
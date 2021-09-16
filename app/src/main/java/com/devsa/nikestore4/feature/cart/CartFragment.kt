package com.devsa.nikestore4.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEy
import com.devsa.nikestore4.common.NikeCompletableObserver
import com.devsa.nikestore4.common.NikeFragment
import com.devsa.nikestore4.data.CartItem
import com.devsa.nikestore4.feature.auth.AuthActivity
import com.devsa.nikestore4.feature.main.detail.ProductDetailActivity
import com.devsa.nikestore4.feature.shipping.ShippingActivity
import com.devsa.nikestore4.services.imageLoading.ImageLoadingService
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class CartFragment:NikeFragment(),CartAdapter.CartItemViewCallBack {

    val cartViewModel:CartViewModel by viewModel()

    var cartAdapter:CartAdapter?=null
    val imageLoadingService: ImageLoadingService by inject()

    val compositeDisposable= CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cart_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cart_items_rv=view.findViewById<RecyclerView>(R.id.cart_items_rv)
        val pay_btn=view.findViewById<ExtendedFloatingActionButton>(R.id.pay_btn)
        val emptyStateRootView=view.findViewById<FrameLayout>(R.id.emptyStateRootView)


        cartViewModel.progrssBarShowLiveData.observe(viewLifecycleOwner,{
            setProgressIndicator(it)
        })

        cartViewModel.cartItemLiveData.observe(viewLifecycleOwner,{
            Timber.i(it.toString())

            cart_items_rv.layoutManager=
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
            cartAdapter= CartAdapter(imageLoadingService,this,it as MutableList<CartItem>)
            cart_items_rv.adapter=cartAdapter
        })

        pay_btn.setOnClickListener {
            startActivity(Intent(requireContext(),ShippingActivity::class.java).apply {
                putExtra(EXTRA_KEy,cartViewModel.purchaseDetailLiveData.value)
            })
        }

        cartViewModel.emptyStateLiveData.observe(viewLifecycleOwner,{
            if(it.mustShow){
                val emptyState=showEmptyState(R.layout.view_cart_empty_state)
                emptyState?.let {view->
                    val emptyStateMessageTv=view.findViewById<TextView>(R.id.emptyStateMessageTv)
                    val emptyStateCtaBtn=view.findViewById<TextView>(R.id.emptyStateCtaBtn)
                    emptyStateMessageTv.text=getString(it.messageResId)
                    emptyStateCtaBtn.visibility=if(it.mustShowCtBtn ) View.VISIBLE else View.GONE
                    emptyStateCtaBtn.setOnClickListener {
                        startActivity(Intent(requireContext(), AuthActivity::class.java))
                    }
                }
            } else{
                emptyStateRootView?.visibility=View.GONE
            }

        })

        cartViewModel.purchaseDetailLiveData.observe(viewLifecycleOwner,{
            Timber.i(it.toString())


            cartAdapter?.let { adapter->
                adapter.purchaseDetail=it
                adapter.notifyItemChanged(adapter.cartItems.size)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        cartViewModel.refresh()
    }

    override fun onRemoveItem(cartItem: CartItem) {
        cartViewModel.removeItemFromCart(cartItem).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter?.removeCartItem(cartItem)
                }
            })
    }

    override fun onIncreaseCartItem(cartItem: CartItem) {
        cartViewModel.increaseCartItem(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter?.incresaseCountCartItem(cartItem)
                }
            })
    }

    override fun onDecreaseCartItem(cartItem: CartItem) {
        cartViewModel.decreaseCartItem(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter?.decreseCountCartItem(cartItem)
                }
            })
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEy,cartItem.product)
        })
    }
}
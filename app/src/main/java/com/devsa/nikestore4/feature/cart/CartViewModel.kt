package com.devsa.nikestore4.feature.cart

import androidx.lifecycle.MutableLiveData
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.NikeSibgleObserver
import com.devsa.nikestore4.common.NikeViewModel
import com.devsa.nikestore4.common.asyncNetworkRequest
import com.devsa.nikestore4.data.*
import com.devsa.nikestore4.data.repo.CartRepository
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus

class CartViewModel(val cartRepository: CartRepository) : NikeViewModel() {

    val cartItemLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyStates>()


    private fun getCartItems() {
        progrssBarShowLiveData.postValue(true)
        if (!TokenContainer.token.isNullOrEmpty()) {
            emptyStateLiveData.value= EmptyStates( mustShow = false)
            cartRepository.get().asyncNetworkRequest()
                .doFinally { progrssBarShowLiveData.value = false }
                .subscribe(object : NikeSibgleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.total_price, t.shipping_cost, t.payable_price)
                        }else{
                            emptyStateLiveData.value= EmptyStates(true, R.string.cartEmptyState)
                        }
                    }
                })

        }else{
            emptyStateLiveData.value= EmptyStates(true, R.string.cartEmptyStateLoginRequired,true)

        }
    }

    fun removeItemFromCart(cartItem: CartItem): Completable {
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                calculatePurchaseDetail()

                var cartItemCount= EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count-=cartItem.count
                    EventBus.getDefault().postSticky(it)
                }

                cartItemLiveData.value?.let {
                    if(it.isEmpty())
                        emptyStateLiveData.postValue(EmptyStates(true,R.string.cartEmptyState))
                }
            }
            .ignoreElement()
    }

    fun increaseCartItem(cartItem: CartItem): Completable {
        return cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess {
                calculatePurchaseDetail()
                var cartItemCount=EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count+=1
                    EventBus.getDefault().postSticky(it)
                }
            }
            .ignoreElement()
    }

    fun decreaseCartItem(cartItem: CartItem): Completable {
        return cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess {
                calculatePurchaseDetail()
                var cartItemCount=EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count-=1
                    EventBus.getDefault().postSticky(it)
                }
            }
            .ignoreElement()
    }

    fun refresh() {
        getCartItems()
    }

    private fun calculatePurchaseDetail() {
        cartItemLiveData.value?.let { cartItems ->
            purchaseDetailLiveData.value?.let {purchaseDetail->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                purchaseDetail.total_price = totalPrice
                purchaseDetail.payable_price = payablePrice
                purchaseDetailLiveData.postValue(purchaseDetail)
            }

        }
    }


}
package com.devsa.nikestore4.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devsa.nikestore4.R
import io.reactivex.disposables.CompositeDisposable

abstract class NikeFragment: NikeView,Fragment(){

    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context

}

abstract class NikeActivitiy: NikeView,AppCompatActivity(){



    override val viewContext: Context?
        get() = this

    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup=window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout){
                viewGroup.children.forEach {
                    if(it is CoordinatorLayout){
                        return  it
                    }
                }
                throw IllegalStateException("root view should be instace of coordinator layout")
            }else
                return viewGroup
        }

}

interface NikeView{
    val rootView:CoordinatorLayout?
    val viewContext:Context?

    fun setProgressIndicator(mustShow:Boolean){
        rootView?.let {
            viewContext?.let { context ->
                var loadingView=it.findViewById<View>(R.id.view_loading)

                if(loadingView ==null &&  mustShow){
                    loadingView=LayoutInflater.from(context).inflate(R.layout.loading_view,it,false)
                    it.addView(loadingView)
                }
                loadingView?.visibility=if(mustShow) View.VISIBLE else View.GONE

            }


        }
    }
}

abstract class NikeViewModel:ViewModel(){
    val compositeDisposable=CompositeDisposable()
    val progrssBarShowLiveData= MutableLiveData<Boolean>()



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
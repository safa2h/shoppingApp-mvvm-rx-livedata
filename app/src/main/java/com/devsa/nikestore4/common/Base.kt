package com.devsa.nikestore4.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devsa.nikestore4.R
import com.devsa.nikestore4.feature.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NikeFragment: NikeView,Fragment(){

    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(nikeException: NikeException) {
        viewContext?.let {
            when (nikeException.type) {
                NikeException.Type.SIMPLE -> showSnackbar(
                    nikeException.serverMessgae ?: it.getString(nikeException.userFreindlyMessgae)
                )
                NikeException.Type.AUTH -> {

                    it.startActivity(Intent(it, AuthActivity::class.java))
                    Toast.makeText(it, nikeException.serverMessgae, Toast.LENGTH_SHORT).show()
                }

                else -> Toast.makeText(it,"else error", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun showEmptyState(layoutResId:Int):View?{
        rootView?.let {
            viewContext ?.let { contex->

                var emptyState=it.findViewById<View>(R.id.emptyStateRootView)
                if(emptyState==null){
                    emptyState=LayoutInflater.from(contex).inflate(layoutResId,it,false)
                    it.addView(emptyState)
                }
                emptyState.visibility=View.VISIBLE
                return  emptyState
            }
        }
        return  null
    }

    fun showSnackbar(message:String,length:Int= Snackbar.LENGTH_SHORT){
        rootView?.let {
            Snackbar.make(it,message,length).show()
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
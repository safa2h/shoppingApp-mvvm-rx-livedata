package com.devsa.nikestore4.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.devsa.nikestore4.R

class NikeToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var onBackButtonClickListener: OnClickListener?=null
    set(value)  {
        field=value
        val back_btn=findViewById<ImageView>(R.id.back_btn)
        back_btn.setOnClickListener(onBackButtonClickListener)
    }


    init {


        inflate(context, R.layout.view_toolbar,this)
        val toolbarTitleTv=findViewById<TextView>(R.id.toolbarTitleTv)
        if(attrs !=null){
            val a=context.obtainStyledAttributes(attrs,R.styleable.NikeToolbar)
            val title=a.getString(R.styleable.NikeToolbar_nikeToolbarTitle)
            if(title !=null){
                toolbarTitleTv.text=title
            }
            a.recycle()
        }
    }
}
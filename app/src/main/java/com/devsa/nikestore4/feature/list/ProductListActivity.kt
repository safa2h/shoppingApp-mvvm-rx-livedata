package com.devsa.nikestore4.feature.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEy
import com.devsa.nikestore4.data.Product
import com.devsa.nikestore4.feature.main.ProdudtAdapter
import com.devsa.nikestore4.feature.main.VIEW_TYPE_LARGE
import com.devsa.nikestore4.feature.main.VIEW_TYPE_SMALL
import com.devsa.nikestore4.feature.main.detail.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.ArrayList

class ProductListActivity : AppCompatActivity(),ProdudtAdapter.OnProductListener {
    val productListViewModel:ProductListViewModel by viewModel { parametersOf(intent.extras!!.getInt(EXTRA_KEy))  }
    val productAdapter:ProdudtAdapter by inject { parametersOf(VIEW_TYPE_SMALL)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        productAdapter.productListener=this


        val rv_listProduct: RecyclerView =findViewById(R.id.rv_productsListActivity)
        val viewTypeChanger: ImageView =findViewById(R.id.viewTypeChangeButton)
        val sortBtn: View =findViewById(R.id.sortBtn)
        val textView2: TextView =findViewById(R.id.textView2)
        val gridLayoutManager=GridLayoutManager(this,2)

        viewTypeChanger.setOnClickListener {
            if(productAdapter.viewType== VIEW_TYPE_SMALL){
                viewTypeChanger.setImageResource(R.drawable.ic_view_type_large)
                productAdapter.viewType=VIEW_TYPE_LARGE
                gridLayoutManager.spanCount=1
                productAdapter.notifyDataSetChanged()
            }else{
                viewTypeChanger.setImageResource(R.drawable.ic_grid)
                productAdapter.viewType= VIEW_TYPE_SMALL
                gridLayoutManager.spanCount=2
                productAdapter.notifyDataSetChanged()
            }

        }


        sortBtn.setOnClickListener {
            val materialAlertDialogBuilder=
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.sort))
                    .setSingleChoiceItems(R.array.sort,productListViewModel.sort)
                    {
                            dialog,selectedSortIndex->
                        productListViewModel.onSelectedSortByUser(selectedSortIndex)
                        dialog.dismiss()

                    }
            materialAlertDialogBuilder.show()
        }
        productListViewModel.selectedLiveData.observe(this){
            textView2.text=getString(it)
        }




        productListViewModel.productLiveDta.observe(this,{
             Timber.i(it.toString())
             rv_listProduct.layoutManager=gridLayoutManager
             productAdapter.products= it as ArrayList<Product>
             rv_listProduct.adapter=productAdapter
         })
    }

    override fun onProductClickListener(product: Product) {
       startActivity(Intent(this,ProductDetailActivity::class.java).apply {
           putExtra(EXTRA_KEy,product)
       })
    }

    override fun onFavoriteClickListener(product: Product) {
        productListViewModel.addToFavorite(product)
    }
}
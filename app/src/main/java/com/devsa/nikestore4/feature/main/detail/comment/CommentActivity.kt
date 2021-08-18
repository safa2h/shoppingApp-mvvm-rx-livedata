package com.devsa.nikestore4.feature.main.detail.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.common.EXTRA_KEY_ID
import com.devsa.nikestore4.common.NikeActivitiy
import com.devsa.nikestore4.data.Comment
import com.devsa.nikestore4.feature.main.detail.CommentAdapter
import com.devsa.nikestore4.view.NikeToolbar
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentActivity : NikeActivitiy() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val commentViewModel:CommentViewModel by viewModel { parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))  }
        val commentAdapter: CommentAdapter by inject{parametersOf(true)}

        val commentsRv: RecyclerView =findViewById(R.id.comments_rv)
        commentsRv.layoutManager= LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        val nikeToolbar=findViewById<NikeToolbar>(R.id.comment_list_toolbar)
        nikeToolbar.onBackButtonClickListener= View.OnClickListener { finish() }


        commentViewModel.commentLiveData.observe(this,{
            commentAdapter.comments= it as ArrayList<Comment>
            commentsRv.adapter=commentAdapter
        })

        commentViewModel.progrssBarShowLiveData.observe(this,{
            setProgressIndicator(it)
        })


    }
}
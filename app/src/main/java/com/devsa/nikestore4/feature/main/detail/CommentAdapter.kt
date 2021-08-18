package com.devsa.nikestore4.feature.main.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devsa.nikestore4.R
import com.devsa.nikestore4.data.Comment

class CommentAdapter(val mustShowAll:Boolean=false): RecyclerView.Adapter<CommentAdapter.CommentViewholder>() {
    var comments=ArrayList<Comment>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewholder {
       return  CommentViewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false))
    }

    override fun onBindViewHolder(holder: CommentViewholder, position: Int) {
       holder.onBindComment(comments[position])
    }

    override fun getItemCount(): Int {
        return  if(comments.size>3&& !mustShowAll) 3 else comments.size
    }

    inner  class CommentViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val commentTitle: TextView =itemView.findViewById(R.id.commentTitleTv_item)
        val commentDateTv: TextView =itemView.findViewById(R.id.comment_data_tv)
        val commentContent: TextView =itemView.findViewById(R.id.commnet_content)
        val commentAuther: TextView =itemView.findViewById(R.id.comment_auther_tv)
        fun onBindComment(comment: Comment){
            commentTitle.text=comment.title
            commentDateTv.text=comment.date
            commentContent.text=comment.content
            commentAuther.text=comment.author.email
        }

    }



}
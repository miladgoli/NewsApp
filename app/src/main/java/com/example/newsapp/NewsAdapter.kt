package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Response

class NewsAdapter(var list: List<Article?>? ,var onclick: onClickNews): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView=itemView.findViewById(R.id.textViewTitleNew)
        val image:ImageView=itemView.findViewById(R.id.imageViewNew)
        val author:TextView=itemView.findViewById(R.id.textViewAuthor)
        val date:TextView=itemView.findViewById(R.id.textViewDate)

        fun bindItem(article: Article){

            title.text=article.title

            if (article.author==null){
                author.text="Unknown...!"
            }else{
                author.text=article.author
            }

            date.text=article.publishedAt

            Picasso.with(itemView.context).load(article.urlToImage).error(R.drawable.news)
                .placeholder(R.drawable.news).centerCrop().fit().into(image)

            itemView.setOnClickListener {
                onclick.onClicked(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindItem(list?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    interface onClickNews{
        fun onClicked(article: Article)
    }
}
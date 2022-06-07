package com.example.newsapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.databinding.ActivityNewBinding
import com.squareup.picasso.Picasso


class NewActivity : AppCompatActivity() {

    lateinit var binding:ActivityNewBinding
    lateinit var article:Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        article= intent.getParcelableExtra<Article>("article")!!

        Picasso.with(this).load(article.urlToImage).error(R.drawable.news)
            .placeholder(R.drawable.news).centerCrop().fit().into(binding.imageviewNew)

        binding.txtTitleNew.text=article.title

        if (article.description!=null){
            binding.txtDescriptionNew.text=article.description
        }else{
            binding.txtDescriptionNew.visibility=View.GONE
        }

        if (article.content!=null){
            binding.txtContentNew.text=article.content+"\nfor more info follow source link...!"
        }else{
            binding.txtContentNew.visibility=View.GONE
        }

        if (article.author==null){
            binding.txtAuthorNew.text="Unknown...!"
        }else{
            binding.txtAuthorNew.text=article.author
        }

        if (article.source!=null){
            binding.txtSourceNew.text=article.url

            binding.txtSourceNew.setOnClickListener {
                val url = article.url
                try {
                    val uri: Uri = Uri.parse("googlechrome://navigate?url=$url")
                    val i = Intent(Intent.ACTION_VIEW, uri)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(i)
                } catch (e: ActivityNotFoundException) {
                    // Chrome is probably not installed
                }
            }
        }else{
            binding.txtSourceNew.visibility= View.GONE
        }

    }

    override fun onBackPressed() {
        finish()
    }
}
package com.example.newsapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.entity.Article
import com.example.newsapp.model.entity.New
import com.example.newsapp.viewmodel.NewViewModel
import com.example.newsapp.viewmodel.NewViewModelProvider
import kotlinx.coroutines.Job


class MainActivity : AppCompatActivity(), NewsAdapter.onClickNews {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: NewViewModel
    private lateinit var requestJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, NewViewModelProvider()).get(NewViewModel::class.java)

        requestJob = viewModel.getNews()

        viewModel.getNewsLiveData.observe(this, Observer { response ->

            adapter = NewsAdapter(response.articles, this)

            setupRecyclerView(adapter)

            stateProgressBar(true)
        })

        viewModel.getErrorLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            stateProgressBar(false)
        })

    }

    fun stateProgressBar(state: Boolean) {

        if (state) {
            binding.progressLoad.visibility = View.GONE
            binding.textViewErrorLoad.visibility = View.GONE
        } else {
            binding.progressLoad.visibility = View.GONE
            binding.textViewErrorLoad.visibility = View.VISIBLE
        }

    }

    fun setupRecyclerView(adapter: NewsAdapter) {

        binding.recViewMain.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recViewMain.adapter = adapter

    }

    override fun onClicked(article: Article) {
        val intent = Intent(this@MainActivity, NewActivity::class.java)
        intent.putExtra("article", article)
        startActivity(intent)
    }

    override fun onDestroy() {
        requestJob.cancel()
        super.onDestroy()
    }
}

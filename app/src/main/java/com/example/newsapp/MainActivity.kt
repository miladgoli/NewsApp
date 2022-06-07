package com.example.newsapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val TAG = "Milad"
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var list: List<New>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY


        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->

                val oldRequest = chain.request()
                val newRequest = oldRequest.newBuilder()

                newRequest.addHeader(
                    "X-Api-Key",
                    "9b9e3d3eb2b344799032828ba4d768f2"
                )

                newRequest.method(oldRequest.method, oldRequest.body)
                chain.proceed(newRequest.build())

            }
            .readTimeout(5,TimeUnit.SECONDS)
            .connectTimeout(5,TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org/v2/")
            .client(okHttpClient)
            .build()


        val newsRetrofit: NewsApiRetrofit = retrofit.create(NewsApiRetrofit::class.java)

        newsRetrofit.getNews().enqueue(object : Callback<New> {

            override fun onResponse(call: Call<New>, response: Response<New>) {

                adapter = NewsAdapter(response.body()?.articles)

                binding.recViewMain.layoutManager =
                    LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                binding.recViewMain.adapter = adapter

                Log.i(TAG, response.toString())

                binding.progressLoad.visibility=View.GONE
                binding.textViewErrorLoad.visibility=View.GONE

            }

            override fun onFailure(call: Call<New>, t: Throwable) {
                Log.i(TAG, "Error" + t.message.toString())
                binding.progressLoad.visibility=View.GONE
                binding.textViewErrorLoad.visibility=View.VISIBLE
            }

        })

    }
}
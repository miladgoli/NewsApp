package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.model.api.NewsApiRetrofit
import com.example.newsapp.model.repository.NewRepository
import com.example.newsapp.model.repository.NewRepositoryImp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NewViewModelProvider : ViewModelProvider.Factory {

    lateinit var newsRetrofit: NewsApiRetrofit
    lateinit var newRepository: NewRepository

    fun initializeApi() {

        val interceptor = HttpLoggingInterceptor()
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
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org/v2/")
            .client(okHttpClient)
            .build()

        newsRetrofit = retrofit.create(NewsApiRetrofit::class.java)
        newRepository = NewRepositoryImp(newsRetrofit)

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        initializeApi()
        return NewViewModel(newRepository) as T
    }
}
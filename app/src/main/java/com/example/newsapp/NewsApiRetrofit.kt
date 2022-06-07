package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET

interface NewsApiRetrofit {
    @GET("https://newsapi.org/v2/top-headlines?country=us&apiKey=9b9e3d3eb2b344799032828ba4d768f2")
    fun getNews(): Call<New>
}
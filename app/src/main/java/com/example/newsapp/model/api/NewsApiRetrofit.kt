package com.example.newsapp.model.api

import com.example.newsapp.model.entity.New
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiRetrofit {
    @GET("top-headlines?country=us&apiKey=9b9e3d3eb2b344799032828ba4d768f2")
    suspend fun getNews(): Response<New>
}
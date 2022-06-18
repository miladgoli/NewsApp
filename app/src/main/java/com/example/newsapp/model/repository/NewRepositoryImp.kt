package com.example.newsapp.model.repository

import com.example.newsapp.model.*
import com.example.newsapp.model.api.NewsApiRetrofit
import com.example.newsapp.model.entity.New
import com.example.newsapp.model.utils.Utils

class NewRepositoryImp(private val apiRetrofit: NewsApiRetrofit) : NewRepository {


    override suspend fun getNewsFromApi(): Response<New, String> {
        val response = apiRetrofit.getNews().body()

        return if (response != null)
            Success(response)
        else
            Failure(Utils.UNKNOWN_ERROR)

    }

}
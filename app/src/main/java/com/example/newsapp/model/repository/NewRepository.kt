package com.example.newsapp.model.repository

import com.example.newsapp.model.entity.New
import retrofit2.Response

interface NewRepository {
    suspend fun getNewsFromApi(): com.example.newsapp.model.Response<New,String>
}
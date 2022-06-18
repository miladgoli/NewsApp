package com.example.newsapp.model.entity

data class New(
    var articles: List<Article?>?,
    var status: String?,
    var totalResults: Int?
)
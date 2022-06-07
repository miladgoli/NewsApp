package com.example.newsapp

data class New(
    var articles: List<Article?>?,
    var status: String?,
    var totalResults: Int?
)
package com.example.newsapplication.presentation.data.remote.dto

import com.example.newsapplication.presentation.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
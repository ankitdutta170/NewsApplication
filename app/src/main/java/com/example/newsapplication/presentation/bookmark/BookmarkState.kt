package com.example.newsapplication.presentation.bookmark

import com.example.newsapplication.presentation.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
) {
}
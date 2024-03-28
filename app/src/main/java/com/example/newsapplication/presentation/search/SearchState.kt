package com.example.newsapplication.presentation.search

import androidx.paging.PagingData
import com.example.newsapplication.presentation.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val seachQuery:String = "",
    val articles: Flow<PagingData<Article>>? = null
)
package com.example.newsapplication.presentation.domain.repository

import androidx.paging.PagingData
import com.example.newsapplication.presentation.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(sources: List<String>) : Flow<PagingData<Article>>

}
package com.example.newsapplication.presentation.domain.usecase.news

import com.example.newsapplication.presentation.data.local.NewsDao
import com.example.newsapplication.presentation.domain.model.Article
import com.example.newsapplication.presentation.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(url:String): Article? {
        return newsRepository.selectArticle(url)

    }
}
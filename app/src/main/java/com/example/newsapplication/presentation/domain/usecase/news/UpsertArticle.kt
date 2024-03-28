package com.example.newsapplication.presentation.domain.usecase.news

import com.example.newsapplication.presentation.data.local.NewsDao
import com.example.newsapplication.presentation.domain.model.Article
import com.example.newsapplication.presentation.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class UpsertArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article){
        newsRepository.upsertArticle(article)
    }

}
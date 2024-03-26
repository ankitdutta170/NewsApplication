package com.example.newsapplication.presentation.domain.usecase.news

import androidx.paging.PagingData
import com.example.newsapplication.presentation.domain.model.Article
import com.example.newsapplication.presentation.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources:List<String>): Flow<PagingData<Article>>{
        return newsRepository.getNews(sources = sources)
    }

}
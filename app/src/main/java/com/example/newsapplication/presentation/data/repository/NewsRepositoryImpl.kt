package com.example.newsapplication.presentation.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapplication.presentation.data.local.NewsDao
import com.example.newsapplication.presentation.data.remote.NewsApi
import com.example.newsapplication.presentation.data.remote.NewsPagingSource
import com.example.newsapplication.presentation.data.remote.SearchNewsPagingSource
import com.example.newsapplication.presentation.domain.model.Article
import com.example.newsapplication.presentation.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
): NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(",")
                )
            }
        ).flow
    }

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteArticle(article: Article) {
       newsDao.delete(article)
    }

    override  fun selectArticles(): Flow<List<Article>> {
      return newsDao.getArticles()
    }

    override suspend fun selectArticle(url:String): Article?{
        return newsDao.getArticle(url)
    }
}
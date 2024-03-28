package com.example.newsapplication.presentation.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapplication.presentation.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article")
    fun getArticles():Flow<List<Article>>

    @Query("SELECT * FROM Article where url=:url")
    suspend fun getArticle(url:String?):Article


}
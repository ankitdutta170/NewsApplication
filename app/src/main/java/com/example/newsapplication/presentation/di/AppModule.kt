package com.example.newsapplication.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.newsapplication.presentation.data.local.NewsDao
import com.example.newsapplication.presentation.data.local.NewsDatabase
import com.example.newsapplication.presentation.data.local.NewsTypeConverter
import com.example.newsapplication.presentation.data.manager.LocalUserManagerImpl
import com.example.newsapplication.presentation.data.remote.NewsApi
import com.example.newsapplication.presentation.data.repository.NewsRepositoryImpl
import com.example.newsapplication.presentation.domain.manager.LocalUserManager
import com.example.newsapplication.presentation.domain.repository.NewsRepository
import com.example.newsapplication.presentation.domain.usecase.appEntry.AppEntryUseCase
import com.example.newsapplication.presentation.domain.usecase.appEntry.ReadAppEntry
import com.example.newsapplication.presentation.domain.usecase.appEntry.SaveAppEntry
import com.example.newsapplication.presentation.domain.usecase.news.DeleteArticle
import com.example.newsapplication.presentation.domain.usecase.news.GetNews
import com.example.newsapplication.presentation.domain.usecase.news.NewsUseCases
import com.example.newsapplication.presentation.domain.usecase.news.SearchNews
import com.example.newsapplication.presentation.domain.usecase.news.SelectArticle
import com.example.newsapplication.presentation.domain.usecase.news.SelectArticles
import com.example.newsapplication.presentation.domain.usecase.news.UpsertArticle
import com.example.newsapplication.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager
                (application: Application):LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntry(
        localUserManager: LocalUserManager
    ): AppEntryUseCase =
        AppEntryUseCase(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )

    @Provides
    @Singleton
    fun provideNewsApi():NewsApi{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(NewsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi,newsDao: NewsDao):NewsRepository{
        return NewsRepositoryImpl(newsApi,newsDao)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(
        repository: NewsRepository,
        dao:NewsDao
        ):NewsUseCases{
        return NewsUseCases(getNews = GetNews(newsRepository = repository),
            searchNews = SearchNews(newsRepository = repository),
            upsertArticle = UpsertArticle(newsRepository = repository),
            deleteArticle = DeleteArticle(newsRepository = repository),
            selectArticles = SelectArticles(newsRepository = repository),
            selectArticle = SelectArticle(newsRepository = repository)
            )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ):NewsDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

}
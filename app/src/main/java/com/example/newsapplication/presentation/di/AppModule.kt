package com.example.newsapplication.presentation.di

import android.app.Application
import com.example.newsapplication.presentation.data.manager.LocalUserManagerImpl
import com.example.newsapplication.presentation.data.remote.NewsApi
import com.example.newsapplication.presentation.data.repository.NewsRepositoryImpl
import com.example.newsapplication.presentation.domain.manager.LocalUserManager
import com.example.newsapplication.presentation.domain.repository.NewsRepository
import com.example.newsapplication.presentation.domain.usecase.appEntry.AppEntryUseCase
import com.example.newsapplication.presentation.domain.usecase.appEntry.ReadAppEntry
import com.example.newsapplication.presentation.domain.usecase.appEntry.SaveAppEntry
import com.example.newsapplication.presentation.domain.usecase.news.GetNews
import com.example.newsapplication.presentation.domain.usecase.news.NewsUseCases
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
    fun provideNewsRepository(newsApi: NewsApi):NewsRepository{
        return NewsRepositoryImpl(newsApi)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(repository: NewsRepository):NewsUseCases{
        return NewsUseCases(getNews = GetNews(newsRepository = repository))
    }

}
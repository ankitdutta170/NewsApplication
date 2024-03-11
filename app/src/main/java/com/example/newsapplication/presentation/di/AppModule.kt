package com.example.newsapplication.presentation.di

import android.app.Application
import com.example.newsapplication.presentation.data.manager.LocalUserManagerImpl
import com.example.newsapplication.presentation.domain.manager.LocalUserManager
import com.example.newsapplication.presentation.domain.usecase.AppEntryUseCase
import com.example.newsapplication.presentation.domain.usecase.ReadAppEntry
import com.example.newsapplication.presentation.domain.usecase.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    ):AppEntryUseCase =
        AppEntryUseCase(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )

}
package com.example.newsapplication.presentation.domain.usecase

import com.example.newsapplication.presentation.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {

    operator fun invoke():Flow<Boolean>{
        return localUserManager.readAppEntry()
    }

}
package com.example.newsapplication.presentation.domain.usecase

import com.example.newsapplication.presentation.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }

}
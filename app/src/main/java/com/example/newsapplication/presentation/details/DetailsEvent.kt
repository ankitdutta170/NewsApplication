package com.example.newsapplication.presentation.details

import com.example.newsapplication.presentation.domain.model.Article

sealed class DetailsEvent {

    data class  UpsertDeleteArticle(val article:Article): DetailsEvent()

    object RemoveSideEffect: DetailsEvent()



}
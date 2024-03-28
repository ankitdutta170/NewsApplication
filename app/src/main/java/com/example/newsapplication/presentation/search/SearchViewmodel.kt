package com.example.newsapplication.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapplication.presentation.domain.usecase.news.NewsUseCases
import com.example.newsapplication.presentation.domain.usecase.news.SearchNews
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewmodel @Inject constructor(
    private val newsUsecase : NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(seachQuery = event.searchQuery)
            }
            is SearchEvent.SearchNews -> {
                searchNews()
            }

            else -> {}
        }
    }

    private fun searchNews() {
        val articles = newsUsecase.searchNews(
            searchQuery = state.value.seachQuery,
            sources = listOf("abc-news","bbc-news","al-jazeera-english")
        ).cachedIn(viewModelScope)
        _state.value = state.value.copy(articles = articles)
    }


}
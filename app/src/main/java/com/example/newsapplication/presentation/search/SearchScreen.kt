package com.example.newsapplication.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapplication.presentation.common.ArticleList
import com.example.newsapplication.presentation.common.SearchBar
import com.example.newsapplication.presentation.domain.model.Article

@Composable
fun SearchScreen(
    state:SearchState,
    event:(SearchEvent) -> Unit,
    navigateToDetails:(Article) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = 24.dp,
                start = 24.dp,
                end = 24.dp
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(text = state.seachQuery,
            readOnly = false,
            onValueChange ={event(SearchEvent.UpdateSearchQuery(it))},
            onSearch = {event(SearchEvent.SearchNews)}
            )
        
        Spacer(modifier = Modifier.height(30.dp))
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticleList(articles = articles, onClick = {navigateToDetails(it)})
        }
    }


}
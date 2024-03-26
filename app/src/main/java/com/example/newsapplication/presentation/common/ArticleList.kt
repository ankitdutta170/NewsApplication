package com.example.newsapplication.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapplication.presentation.domain.model.Article

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles:LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles = articles)
    if(handlePagingResult){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(all = 3.dp)
        ){
            items(count = articles.itemCount){
                articles[it]?.let{
                    ArticleCard( article = it, onClick = {onClick(it)})
                }
            }
        }
    }

}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>
):Boolean {

    val loadstate = articles.loadState
    val error = when {
            loadstate.refresh is LoadState.Error -> loadstate.refresh as LoadState.Error
            loadstate.prepend is LoadState.Error -> loadstate.prepend as LoadState.Error
            loadstate.append  is LoadState.Error -> loadstate.append as LoadState.Error
            else -> null
    }

    return when{
        loadstate.refresh is LoadState.Loading->{
            ShimmerEffect()
            false
        }
        error != null ->{
            EmptyScreen()
            false
        }
        else->{
            true
        }
    }

    
}

@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        repeat(10){
            ArticleShimmerEffect(
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

    }
    
}
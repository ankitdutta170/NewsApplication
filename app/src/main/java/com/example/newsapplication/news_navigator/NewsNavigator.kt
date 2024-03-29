package com.example.newsapplication.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapplication.R
import com.example.newsapplication.news_navigator.components.BottomNavigationItem
import com.example.newsapplication.news_navigator.components.NewsBottomNavigation
import com.example.newsapplication.presentation.bookmark.BookMarkViewmodel
import com.example.newsapplication.presentation.bookmark.BookmarkScreen
import com.example.newsapplication.presentation.details.DetailsEvent
import com.example.newsapplication.presentation.details.DetailsScreen
import com.example.newsapplication.presentation.details.DetailsViewModel
import com.example.newsapplication.presentation.domain.model.Article
import com.example.newsapplication.presentation.home.HomeScreen
import com.example.newsapplication.presentation.home.HomeViewmodel
import com.example.newsapplication.presentation.navgraph.Route
import com.example.newsapplication.presentation.search.SearchScreen
import com.example.newsapplication.presentation.search.SearchViewmodel

@Composable
fun NewsNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )

    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = when(backStackState?.destination?.route){
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
    }

    val isBottomBarVisible = remember(key1 = backStackState){
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if(isBottomBarVisible){
                NewsBottomNavigation(items = bottomNavigationItems,
                    selected = selectedItem ,
                    onItemClick = {index ->
                        when(index){
                            0 -> navigateToTap(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1-> navigateToTap(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTap(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }

                    })
            }
        }
    ){
        val bottomPadding = it.calculateBottomPadding()
        NavHost(navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
            ){
                composable(route = Route.HomeScreen.route){
                    val viewmodel: HomeViewmodel = hiltViewModel()
                    val articles = viewmodel.news.collectAsLazyPagingItems()
                    HomeScreen(articles = articles,
                        navigateToSearch = {
                            navigateToTap(navController=navController, route = Route.SearchScreen.route)
                        },
                        navigateToDetails = {article ->
                            navigateToDetails(
                                navController = navController,
                                article = article
                            )

                        }
                        )
                }
            composable(route = Route.SearchScreen.route){
                val viewmodel: SearchViewmodel = hiltViewModel()
                val state = viewmodel.state.value
                SearchScreen(state = state, event = viewmodel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(navController=navController, article = it)
                    })
            }

            composable(route = Route.DetailsScreen.route){
                val viewmodel: DetailsViewModel = hiltViewModel()
                if(viewmodel.sideEffect != null){
                    Toast.makeText(LocalContext.current,viewmodel.sideEffect,Toast.LENGTH_LONG).show()
                    viewmodel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let {article ->
                    DetailsScreen(article = article, event = viewmodel::onEvent,
                        navigateUp = {navController.navigateUp()}
                        )
                }
            }

            composable(route = Route.BookmarkScreen.route){
                val viewmodel:BookMarkViewmodel = hiltViewModel()
                val state = viewmodel.state.value
                BookmarkScreen(state = state, navigateToDetails ={
                    navigateToDetails(navController=navController, article = it)
                } )
            }

        }
    }

}

private fun navigateToTap(navController: NavController,route: String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let {homeScreen->
            popUpTo(homeScreen){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article){
    navController.currentBackStackEntry?.savedStateHandle?.set("article",article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}


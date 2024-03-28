package com.example.newsapplication.presentation.navgraph

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapplication.news_navigator.NewsNavigator
import com.example.newsapplication.presentation.home.HomeScreen
import com.example.newsapplication.presentation.home.HomeViewmodel
import com.example.newsapplication.presentation.onboarding.OnBoardingScreen
import com.example.newsapplication.presentation.onboarding.OnBoardingViewModel
import com.example.newsapplication.presentation.search.SearchScreen
import com.example.newsapplication.presentation.search.SearchViewmodel

@Composable
fun NavGraph(
    startDestination: String,
    context: Context
) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = startDestination ){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ){
                val viewmodel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(event = viewmodel::onEvent)
            }
        }
        
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigationScreen.route
        ){
            composable(route = Route.NewsNavigationScreen.route){
                val viewModel: HomeViewmodel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(articles = articles, navigateToDetails = {}, navigateToSearch = {})
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigationScreen.route
        ){
            composable(route = Route.NewsNavigationScreen.route){
                NewsNavigator()
            }
        }
    }
    
}
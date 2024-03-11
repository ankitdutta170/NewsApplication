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
import com.example.newsapplication.presentation.onboarding.OnBoardingScreen
import com.example.newsapplication.presentation.onboarding.OnBoardingViewModel

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
                Text(text = "News Navigation")
                Toast.makeText(context,"HomeScreen",Toast.LENGTH_LONG).show()
            }
        }
    }
    
}
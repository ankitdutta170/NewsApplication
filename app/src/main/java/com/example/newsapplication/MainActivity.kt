package com.example.newsapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.newsapplication.presentation.navgraph.NavGraph
import com.example.newsapplication.ui.theme.NewsApplicationTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { viewModel.splashCondition.value })
        }




        setContent {
            NewsApplicationTheme {

                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                Scaffold(

                ) {
                    Box(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                    ) {
                        val startDestination = viewModel.startDestination.value
                        NavGraph(startDestination = startDestination, this@MainActivity)
                    }
                }

            }
        }
    }
}

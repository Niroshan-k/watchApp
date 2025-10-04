package com.example.testapp

import CartPage
import HomePage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.model.Watch

import com.example.testapp.screen.*
import com.example.testapp.ui.theme.TestAppTheme

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.viewmodel.WatchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAppTheme {
                val navController = rememberNavController()
                val watchViewModel: WatchViewModel = viewModel() // <-- get ViewModel instance

                NavHost(navController = navController, startDestination = "home") {
                    composable("launch") { LaunchPage(navController) }
                    composable("home") { HomePage(navController, watchViewModel) }
                    composable("favourites") { FavouritePage(navController) }
                    composable("cart") { CartPage(navController) }
                    composable("profile") { ProfilePage(navController) }
                    composable("login") { LoginPage(navController) }
                    composable("register") { RegisterPage(navController) }
                    composable("productDescription/{watchId}") { backStackEntry ->
                        val watchId = backStackEntry.arguments?.getString("watchId")
                        val watch = watchViewModel.findWatchById(watchId)
                        if (watch != null) {
                            ProductDescriptionPage(navController, watch)
                        }
                    }
                }
            }
        }
    }
}


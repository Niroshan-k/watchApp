package com.example.testapp

import CartPage
import HomePage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.testapp.screen.*
import com.example.testapp.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                // Create a NavController to manage navigation
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "launch") {
                    composable("launch") { LaunchPage(navController) }
                    composable("home") { HomePage(navController) }
                    composable("favourites") { FavouritePage(navController) }
                    composable("cart") { CartPage(navController) }
                    composable("profile") { ProfilePage(navController) }
                    composable("login") { LoginPage(navController) }
                    composable("register") { RegisterPage(navController) }
                    composable("productDescription") { ProductDescriptionPage(navController) }

                }
            }
        }
    }
}

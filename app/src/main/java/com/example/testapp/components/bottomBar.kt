package com.example.testapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testapp.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomAppBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_home),
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { navController.navigate("favourites") }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_heart),
                    contentDescription = "Favourites",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { navController.navigate("cart") }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_cart),
                    contentDescription = "Cart",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_profile),
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

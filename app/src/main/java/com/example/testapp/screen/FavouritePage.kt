package com.example.testapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testapp.R
import com.example.testapp.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritePage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favourite_items_title),
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Sample data for favourite watches
            val favouriteWatches = listOf(
                FavouriteWatch(R.drawable.watch1, "Tissot", "$299"),
                FavouriteWatch(R.drawable.watch2, "Rado", "$185"),// First watch
                FavouriteWatch(R.drawable.watch3, "Mavado", "$399") // Second watch
            )

            LazyColumn {
                items(favouriteWatches) { watch ->
                    FavouriteItem(
                        imageRes = watch.imageRes,
                        price = watch.price,
                        name = watch.name,
                        onRemove = { /* Handle remove item */ }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun FavouriteItem(
    imageRes: Int,
    price: String,
    name: String,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .size(114.dp)
                    .padding(end = 16.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = price,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onRemove) {
                Icon(
                    painter = painterResource(id = R.drawable.fav), // Replace with your icon
                    contentDescription = stringResource(R.string.remove_item_description),
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp) // Adjust the size here
                )
            }
        }
    }
}

// Data class for favourite watches
data class FavouriteWatch(
    val imageRes: Int,
    val name: String,
    val price: String
)

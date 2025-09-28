package com.example.testapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.testapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDescriptionPage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_to_home))
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle favorite action */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = stringResource(R.string.favorite))
                    }
                }
            )
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(0.dp)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.surface)

        ) {
            Image(
                painter = painterResource(id = R.drawable.w_11),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.product_name), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.product_code), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.product_description),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.product_price), style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.quantity_label), style = MaterialTheme.typography.bodyMedium)
                Text("1", style = MaterialTheme.typography.bodyMedium)
                IconButton(onClick = { /* Increase quantity */ }) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.increase_quantity))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Handle checkout */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline)
            ) {
                Text(text = stringResource(R.string.checkout_button), color = MaterialTheme.colorScheme.scrim)
            }
        }
    }
}

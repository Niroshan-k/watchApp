package com.example.testapp.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.testapp.model.Watch
import com.example.testapp.R

val dmSerifFont = FontFamily(Font(R.font.dm_serif_display_regular))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDescriptionPage(
    navController: NavController,
    watch: Watch // <-- Pass Watch object from navigation or state!
) {

    val context = LocalContext.current
    val shareText = """
                    Check out this watch!
                    Brand: ${watch.brand}
                    Model: ${watch.model}
                    Price: $${watch.price}
                    Features: ${watch.features.joinToString(", ")}
                    Description: ${watch.description}
                    """.trimIndent()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "PRODUCT DETAILS",
                        fontFamily = dmSerifFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = 2.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back to home"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Shopping cart action */ }) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Cart"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Watch image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(Color(0xFFEAEAEA), RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(watch.imageUrl),
                    contentDescription = "${watch.brand} ${watch.model}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(18.dp))

            // Name & Model
            Text(
                text = watch.brand,
                fontFamily = dmSerifFont,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = watch.model,
                fontFamily = dmSerifFont,
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(Modifier.height(20.dp))

            // Price
            Text(
                text = "$${watch.price}",
                fontFamily = dmSerifFont,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color(0xFF7B4B1A),
                modifier = Modifier
                    .padding(10.dp)
            )

            Spacer(Modifier.height(6.dp))

            // Ratings
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 20.dp)
            ) {
                repeat(5) {
                    Icon(
                        painter = painterResource(id = R.drawable.star), // use your star drawable!
                        contentDescription = "Star",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = " 1289 reviews",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            // Description
            Text(
                text = watch.description,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .background(Color(0xFFF8F8F8), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Features & Details
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4)),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text(
                        text = "FEATURES",
                        fontFamily = dmSerifFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF7B4B1A)
                    )
                    Spacer(Modifier.height(6.dp))
                    Row {
                        Text("Water resistance: ", fontWeight = FontWeight.Bold)
                        Text("${watch.waterResistance}m")
                    }
                    Row {
                        Text("Movement: ", fontWeight = FontWeight.Bold)
                        Text(watch.movement)
                    }
                    Row {
                        Text("Case: ", fontWeight = FontWeight.Bold)
                        Text(watch.caseMaterial)
                    }
                    Row {
                        Text("Strap: ", fontWeight = FontWeight.Bold)
                        Text(watch.strapMaterial)
                    }
                    Row {
                        Text("Year: ", fontWeight = FontWeight.Bold)
                        Text("${watch.releaseYear}")
                    }
                    Row {
                        Text("Dial: ", fontWeight = FontWeight.Bold)
                        Text(watch.dialColor)
                    }
                    Row {
                        Text("Diameter: ", fontWeight = FontWeight.Bold)
                        Text("${watch.caseDiameter}mm")
                    }
                    if (watch.features.isNotEmpty()) {
                        Text(
                            text = "Extra: " + watch.features.joinToString(", "),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Bottom actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Add to cart
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ADD TO CART button (wide, dark brown)
                    Button(
                        onClick = { /* Add to cart */ },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B4B1A)),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {
                        Text(
                            text = "ADD TO CART",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = dmSerifFont,
                            fontSize = 18.sp,
                            letterSpacing = 2.sp
                        )
                    }

                    // Spacer between buttons
                    Spacer(modifier = Modifier.width(16.dp))

                    // Share button (circle, fixed size, height = width = 52.dp)
                    Button(
                        onClick = { navController.navigate("contacts?message=${Uri.encode(shareText)}") },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B4B1A)),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(52.dp)
                    ) {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))
        }
    }
}
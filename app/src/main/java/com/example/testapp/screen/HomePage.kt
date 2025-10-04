import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.testapp.components.BottomNavigationBar
import com.example.testapp.model.Watch
import com.example.testapp.viewmodel.WatchViewModel
import com.example.testapp.R
import com.example.testapp.utils.NetworkUtils

val dmSerifFont = FontFamily(Font(R.font.dm_serif_display_regular))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, watchViewModel: WatchViewModel) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val columns = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
    val context = LocalContext.current

    // Collect watches and error from ViewModel
    val watches by watchViewModel.watches.collectAsState()
    val error by watchViewModel.error.collectAsState()

    // Only fetch once on first load
    LaunchedEffect(Unit) {
        if (watches.isEmpty() && error == null) {
            watchViewModel.loadWatches(context)
        }
    }

    val loading = watches.isEmpty() && error == null

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                // Top Bar and Categories
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "World of luxury",
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontFamily = dmSerifFont
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Find the perfect watch for your wrist",
                        fontSize = 15.sp,
                        color = Color.Gray,
                        fontFamily = dmSerifFont
                    )
                    Spacer(Modifier.height(18.dp))
                    Text(
                        text = "Categories",
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontFamily = dmSerifFont,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        CategoryCard(
                            image = painterResource(id = R.drawable.leather),
                            title = "Leather Strap",
                            count = 23,
                            fontFamily = dmSerifFont,
                            modifier = Modifier.weight(1f)
                        )
                        CategoryCard(
                            image = painterResource(id = R.drawable.metal),
                            title = "Metallic Strap",
                            count = 17,
                            fontFamily = dmSerifFont,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(18.dp))
                    Text(
                        text = "Featured Products",
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontFamily = dmSerifFont,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }
            item {
                // Watches grid section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 400.dp, max = 1200.dp)
                ) {
                    when {
                        loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                        error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(error ?: "", color = Color.Red)
                        }
                        watches.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No watches found.")
                        }
                        else -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(columns),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(watches) { watch ->
                                    WatchCard(
                                        modifier = Modifier
                                            .clickable { navController.navigate("productDescription/${watch.id}") },
                                        watch = watch,
                                        fontFamily = dmSerifFont,
                                        isOnline = NetworkUtils.isNetworkAvailable(context)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun CategoryCard(
    image: Painter,
    title: String,
    count: Int,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .height(180.dp)
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFFEAEAEA), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = image,
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = fontFamily,
                    fontWeight = Bold
                )
                Text(
                    text = "($count)",
                    fontSize = 13.sp,
                    color = Color(0xFF7B4B1A),
                )
            }
        }
    }
}

@Composable
fun WatchCard(
    modifier: Modifier = Modifier,
    watch: Watch,
    fontFamily: FontFamily,
    isOnline: Boolean
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.75f),
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Color(0xFFEAEAEA), RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (isOnline) {
                        Image(
                            painter = rememberAsyncImagePainter(watch.imageUrl),
                            contentDescription = "${watch.brand} ${watch.model}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.offline_wifi),
                            contentDescription = "Offline image",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                    // Discount badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .background(Color(0xFF7B4B1A))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "-10%",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontFamily = fontFamily
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${watch.brand} ${watch.model}",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontFamily = fontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${watch.price}",
                    fontSize = 24.sp,
                    color = Color(0xFF7B4B1A),
                    fontFamily = fontFamily
                )
            }
        }
    }
}
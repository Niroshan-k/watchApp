import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testapp.R
import com.example.testapp.components.BottomNavigationBar

data class Product(val imageRes: Int, val priceRes: Int, val nameRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    val columns = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        3
    } else {
        2
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.outlineVariant)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Image(
                    painter = painterResource(id = R.drawable.watch_logob),
                    contentDescription = stringResource(R.string.logo_content_description),
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(0.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(stringResource(R.string.search_placeholder),color = MaterialTheme.colorScheme.scrim) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.outlineVariant),
        ) {
            val products = listOf(
                Product(R.drawable.watch11, R.string.watch_1_price, R.string.watch_1),
                Product(R.drawable.watch2, R.string.watch_2_price, R.string.watch_2),
                Product(R.drawable.watch3, R.string.watch_3_price, R.string.watch_3),
                Product(R.drawable.watch4, R.string.watch_4_price, R.string.watch_4),
                Product(R.drawable.watch5, R.string.watch_5_price, R.string.watch_5),
                Product(R.drawable.watch6, R.string.watch_6_price, R.string.watch_6)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp), // Adjusted padding for better layout
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between cards horizontally
                verticalArrangement = Arrangement.spacedBy(8.dp) // Space between cards vertically
            ) {
                items(products) { product ->
                    WatchCard(
                        modifier = Modifier
                            .clickable { navController.navigate("productDescription") },
                        imageRes = painterResource(id = product.imageRes),
                        priceRes = product.priceRes,
                        nameRes = product.nameRes
                    )
                }
            }
        }
    }
}

@Composable
fun WatchCard(
    modifier: Modifier = Modifier,
    imageRes: Painter,
    priceRes: Int,
    nameRes: Int
) {
    val price = stringResource(priceRes)
    val name = stringResource(nameRes)

    Card(
        modifier = modifier
            .fillMaxWidth() // Ensure card fills the width of the cell
            .aspectRatio(0.75f), // Maintain a specific aspect ratio for better visual consistency
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imageRes,
                    contentDescription = name,
                    modifier = Modifier.size(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                fontSize = 16.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = price,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

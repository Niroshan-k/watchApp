import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun CartPage(navController: NavController) {
    val cartItems = listOf(
        CartItemData(R.drawable.watch2, "$299", "Rado"),
        CartItemData(R.drawable.watch3, "$399", "Tissot")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cart_page_title),
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { item ->
                        CartItem(
                            imageRes = item.imageRes,
                            price = item.price,
                            name = item.name,
                            onQuantityChange = { /* Handle quantity change */ }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.total_label),
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
                            )
                            Text(
                                text = stringResource(R.string.total_amount), // Replace with actual total
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
                            )
                        }
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
}

data class CartItemData(val imageRes: Int, val price: String, val name: String)

@Composable
fun CartItem(
    imageRes: Int,
    price: String,
    name: String,
    onQuantityChange: (Int) -> Unit
) {
    val quantity = remember { mutableStateOf(1) }

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
            QuantitySelector(
                quantity = quantity.value,
                onQuantityChange = {
                    quantity.value = it
                    onQuantityChange(it)
                }
            )
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            if (quantity > 1) onQuantityChange(quantity - 1)
        }) {
            Text(text = stringResource(R.string.quantity_decrease), style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
        }
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(onClick = { onQuantityChange(quantity + 1) }) {
            Text(text = stringResource(R.string.quantity_increase), style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
        }
    }
}

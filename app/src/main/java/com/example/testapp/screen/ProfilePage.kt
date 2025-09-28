package com.example.testapp.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.testapp.R
import com.example.testapp.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(navController: NavHostController) {
    val orientation = LocalConfiguration.current.orientation

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(stringResource(R.string.profile_title)) },
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

        val scrollState = rememberScrollState()

        val contentModifier = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 32.dp)
        } else {
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        }

        Column(
            modifier = contentModifier
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // Simulate status bar height

            // Header
            Text(
                text = stringResource(R.string.profile_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            // Avatar and User Info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar), // Replace with your avatar drawable
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                        .padding(16.dp)
                )
                Text(text = stringResource(R.string.user_name), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = stringResource(R.string.user_email), fontSize = 16.sp, color = Color.Gray)
            }

            // Options List
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileOption(icon = R.drawable.ic_wallet, text = stringResource(R.string.wallet_option))
                ProfileOption(icon = R.drawable.ic_support, text = stringResource(R.string.support_option))
                ProfileOption(icon = R.drawable.ic_privacy_policy, text = stringResource(R.string.privacy_policy_option))
                ProfileOption(
                    icon = R.drawable.ic_signout,
                    text = stringResource(R.string.sign_out_option),
                    onClick = { navController.navigate("login") }
                )
            }
        }
    }
}

@Composable
fun ProfileOption(
    icon: Int,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon), // Replace with your icon drawable
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 18.sp, modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_check), // Replace with your checkmark drawable
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

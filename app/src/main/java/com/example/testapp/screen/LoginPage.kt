package com.example.testapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.testapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // Main title
        Text(
            text = "Login",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Sub title
        Text(
            text = "Enter your login details below.",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Email TextField
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White


            ),
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_check), contentDescription = null) }
        )

        // Password TextField
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_visibility_off), contentDescription = null) }
        )

        // Remember me and Forgot password
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = false, onCheckedChange = {})
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Remember me",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "Forgot password?",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.clickable { /* Handle forgot password action */ }
            )
        }

        // Sign In Button
        Button(
            onClick = {
                // Navigate to HomePage
                navController.navigate("home")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline)
        ) {
            Text(text = "Sign In", color = MaterialTheme.colorScheme.scrim)
        }

        // Sign Up Link
        Text(
            text = "Don't have an account? Sign Up",
            color = Color.Gray,
            modifier = Modifier
                .padding(top = 24.dp)
                .clickable {
                    navController.navigate("register") // Navigate to the Sign Up page
                }
        )

        // Social Media Icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.ic_facebook), contentDescription = null, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.ic_twitter), contentDescription = null, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.ic_google), contentDescription = null, modifier = Modifier.size(48.dp))
        }
    }
}

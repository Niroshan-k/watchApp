package com.example.testapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapp.R
import com.example.testapp.viewmodels.SigninViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    navController: NavController,
    signinViewModel: SigninViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    val isLoading by signinViewModel.isLoading.collectAsState()
    val errorMsg by signinViewModel.errorMsg.collectAsState()

    fun validateInputs(): Boolean {
        return when {
            email.isBlank() -> {
                localError = "Email cannot be empty."
                false
            }
            password.isBlank() -> {
                localError = "Password cannot be empty."
                false
            }
            else -> {
                localError = null
                true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ){
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
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                ),
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_check), contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password TextField
            TextField(
                value = password,
                onValueChange = { password = it },
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
                    Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Remember me",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = "Forgot password?",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.clickable { /* TODO: Handle forgot password */ }
                )
            }

            // Local error (input validation)
            localError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Error message from Firebase
            errorMsg?.let {
                // You can customize messages here, or show as is
                val message = when {
                    it.contains("password is invalid", ignoreCase = true) ||
                            it.contains("no user record", ignoreCase = true) ||
                            it.contains("wrong password", ignoreCase = true) -> "Incorrect email or password."
                    else -> it
                }
                Text(
                    text = message,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Sign In Button
            Button(
                onClick = {
                    if (validateInputs()) {
                        signinViewModel.signIn(email, password) {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Sign In", color = MaterialTheme.colorScheme.scrim)
                }
            }

            // Sign Up Link
            Text(
                text = "Don't have an account? Sign Up",
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .clickable {
                        navController.navigate("register")
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
}
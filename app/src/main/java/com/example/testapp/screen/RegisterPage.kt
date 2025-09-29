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
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testapp.R
import com.example.testapp.viewmodels.SignupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(
    navController: NavController,
    signupViewModel: SignupViewModel = viewModel()
) {
    // State for text fields
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordMismatchError by remember { mutableStateOf<String?>(null) }

    val isLoading by signupViewModel.isLoading.collectAsState()
    val errorMsg by signupViewModel.errorMsg.collectAsState()

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
            text = "Register",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Name TextField
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            ),
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_check), contentDescription = null) }
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

        // Confirm Password TextField
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_visibility_off), contentDescription = null) }
        )

        // Error message for password mismatch
        passwordMismatchError?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Error message from Firebase
        errorMsg?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Register Button
        Button(
            onClick = {
                passwordMismatchError = null
                if (password != confirmPassword) {
                    passwordMismatchError = "Passwords do not match"
                } else if (email.isNotBlank() && password.isNotBlank()) {
                    signupViewModel.register(email, password) {
                        // On success, navigate to login screen
                        navController.navigate("login") {
                            // Optional: pop up to prevent back navigation
                            popUpTo("register") { inclusive = true }
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
                Text(text = "Register", color = Color.White)
            }
        }

        // Login Link
        Text(
            text = "Already have an account? Login",
            color = Color.Gray,
            modifier = Modifier
                .padding(top = 24.dp)
                .clickable {
                    navController.navigate("login")
                }
        )

        // Social Media Icons (for display only)
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
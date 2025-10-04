package com.example.testapp.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.*
import android.os.BatteryManager
import android.net.wifi.WifiManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.testapp.R
import com.example.testapp.components.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(navController: NavHostController) {
    val context = LocalContext.current

    // --- Battery Info ---
    val batteryPct by remember { mutableStateOf(getBatteryLevel(context)) }

    // --- Network Info ---
    val connectivityInfo = getNetworkInfo(context)
    val wifiInfo = getWifiInfo(context)

    // --- Load from local storage ---
    val prefs = context.getSharedPreferences("profile", Context.MODE_PRIVATE)
    var username by remember { mutableStateOf(prefs.getString("username", "") ?: "") }
    val email = FirebaseAuth.getInstance().currentUser?.email ?: "No email found"
    val password = "********"
    var profilePicUri by remember { mutableStateOf(prefs.getString("profilePicUri", null)) }

    // --- Image picker launcher ---
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            profilePicUri = it.toString()
            saveProfilePic(context, it)
            prefs.edit().putString("profilePicUri", profilePicUri).apply()
        }
    }

    Scaffold(
        topBar = {
            Column {
                SmallTopAppBar(
                    title = { Text("Account", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black
                    )
                )
                // Battery & Network Info Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF2F2F2))
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("🔋 Battery: $batteryPct%", fontWeight = FontWeight.Medium)
                    Text("🌐 ${connectivityInfo.status}", fontWeight = FontWeight.Medium)
                }
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- Profile Picture with Camera Icon ---
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.size(120.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 6.dp,
                        modifier = Modifier.size(110.dp)
                    ) {
                        if (profilePicUri != null) {
                            val bitmap = loadProfileBitmap(context)
                            bitmap?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = "Profile picture",
                                    modifier = Modifier.size(110.dp)
                                )
                            } ?: Icon(
                                painter = painterResource(id = R.drawable.avatar),
                                contentDescription = "Default avatar",
                                modifier = Modifier.size(75.dp),
                                tint = Color(0xFF7B4B1A)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.avatar),
                                contentDescription = "Default avatar",
                                modifier = Modifier.size(75.dp),
                                tint = Color(0xFF7B4B1A)
                            )
                        }
                    }
                    // Camera icon overlay
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Change profile picture",
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White, CircleShape)
                            .padding(4.dp)
                            .clickable { launcher.launch("image/*") }
                    )
                }
                Spacer(Modifier.height(18.dp))

                // --- Form Card ---
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(24.dp)) {
                        Text("Username", fontWeight = FontWeight.Medium, color = Color.Gray)
                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(Modifier.height(18.dp))
                        Text("Email", fontWeight = FontWeight.Medium, color = Color.Gray)
                        Text(
                            email,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(18.dp))
                        Text("Password", fontWeight = FontWeight.Medium, color = Color.Gray)
                        Text(password, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                        Spacer(Modifier.height(24.dp))

                        // --- Update/Remove Buttons ---
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    prefs.edit()
                                        .putString("username", username)
                                        .putString("profilePicUri", profilePicUri)
                                        .apply()
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Update")
                            }
                            Spacer(Modifier.width(16.dp))
                            OutlinedButton(
                                onClick = {
                                    username = ""
                                    profilePicUri = null
                                    prefs.edit().clear().apply()
                                    removeProfilePic(context)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Remove")
                            }
                        }
                    }
                }

                // --- Connection Info Section ---
                Spacer(Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text("📡 Network Information", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Spacer(Modifier.height(12.dp))
                        Text("WiFi Name: ${wifiInfo.ssid}", fontSize = 15.sp)
                        Text("WiFi Speed: ${wifiInfo.linkSpeed} Mbps", fontSize = 15.sp)
                        Text("Signal Strength: ${wifiInfo.signalLevel}", fontSize = 15.sp)
                        Text("Network Status: ${connectivityInfo.status}", fontSize = 15.sp)
                        Text("Connection Type: ${connectivityInfo.type}", fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

// --- Utility functions for profile image ---
fun saveProfilePic(context: Context, uri: Uri) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, "profile_pic.jpg")
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
}

fun loadProfileBitmap(context: Context): Bitmap? {
    val file = File(context.filesDir, "profile_pic.jpg")
    return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
}

fun removeProfilePic(context: Context) {
    val file = File(context.filesDir, "profile_pic.jpg")
    if (file.exists()) file.delete()
}

// --- Utils for Battery, Network, WiFi ---
fun getBatteryLevel(context: Context): Int {
    val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    val level = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    return level // returns percentage
}

data class ConnectivityInfo(val status: String, val type: String)
fun getNetworkInfo(context: Context): ConnectivityInfo {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork
    val capabilities = cm.getNetworkCapabilities(network)
    return if (capabilities != null) {
        val type = when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
            else -> "Other"
        }
        val status = if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) "Online" else "Offline"
        ConnectivityInfo(status, type)
    } else {
        ConnectivityInfo("Offline", "None")
    }
}

data class WifiInfoLocal(val ssid: String, val linkSpeed: Int, val signalLevel: Int)
fun getWifiInfo(context: Context): WifiInfoLocal {
    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val info = wifiManager.connectionInfo
    val ssid = info.ssid?.replace("\"", "") ?: "Unknown"
    val linkSpeed = info.linkSpeed // Mbps
    val rssi = info.rssi
    // Calculate signal level out of 5
    val signalLevel = WifiManager.calculateSignalLevel(rssi, 5)
    return WifiInfoLocal(ssid, linkSpeed, signalLevel)
}
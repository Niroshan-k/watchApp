import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.*

data class Contact(val name: String, val phone: String)

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(navController: NavHostController, message: String) {
    val context = LocalContext.current

    // Accompanist permission state
    val contactsPermissionState = rememberPermissionState(
        android.Manifest.permission.READ_CONTACTS
    )

    // Ask for permission when composable is first composed
    LaunchedEffect(Unit) {
        contactsPermissionState.launchPermissionRequest()
    }

    var contacts by remember { mutableStateOf<List<Contact>?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Only load contacts if permission is granted
    LaunchedEffect(contactsPermissionState.status) {
        if (contactsPermissionState.status.isGranted) {
            loading = true
            try {
                contacts = loadContacts(context)
                if (contacts.isNullOrEmpty()) {
                    error = "No contacts found on device."
                }
            } catch (e: Exception) {
                error = "Error loading contacts: ${e.localizedMessage}"
            }
            loading = false
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text("Select Contact") })
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                !contactsPermissionState.status.isGranted -> {
                    Text(
                        "Contacts permission required to display contacts.",
                        Modifier.padding(24.dp)
                    )
                }
                loading -> {
                    CircularProgressIndicator(Modifier.padding(24.dp))
                }
                error != null -> {
                    Text(error!!, Modifier.padding(24.dp))
                }
                contacts.isNullOrEmpty() -> {
                    Text("No contacts found.", Modifier.padding(24.dp))
                }
                else -> {
                    contacts!!.forEach { contact ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    // Open SMS app with the message and phone number
                                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("smsto:${contact.phone}")
                                        putExtra("sms_body", message)
                                    }
                                    context.startActivity(intent)
                                }
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(text = contact.name, style = MaterialTheme.typography.titleMedium)
                                Text(text = contact.phone, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun loadContacts(context: Context): List<Contact> {
    val contacts = mutableListOf<Contact>()
    val contentResolver = context.contentResolver
    val cursor = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
    )
    cursor?.use {
        val nameIdx = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val phoneIdx = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        while (it.moveToNext()) {
            val name = it.getString(nameIdx)
            val phone = it.getString(phoneIdx)
            contacts.add(Contact(name, phone))
        }
    }
    return contacts
}
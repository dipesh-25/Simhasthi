package com.example.mahakumbhsafetyapp.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.example.mahakumbhsafetyapp.ui.theme.SuccessGreen
import com.example.mahakumbhsafetyapp.ui.theme.ErrorRed

data class EmergencyContact(
    val name: String,
    val phone: String,
    val category: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactsScreen(navController: NavController) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var lastCalledNumber by remember { mutableStateOf("") }
    var lastCalledName by remember { mutableStateOf("") }

    val contacts = listOf(
        EmergencyContact("Police", "100", "Emergency", Icons.Filled.Emergency),
        EmergencyContact("Ambulance", "108", "Medical", Icons.Filled.Call),
        EmergencyContact("Fire", "101", "Fire", Icons.Filled.Call),
        EmergencyContact("Disaster Management", "1070", "Disaster", Icons.Filled.Call),
        EmergencyContact("Women Helpline", "1090", "Safety", Icons.Filled.Call),
        EmergencyContact("Tourist Police", "+91-571-220-2431", "Tourist", Icons.Filled.Call),
        EmergencyContact("Medical Helpline", "+91-571-240-0362", "Medical", Icons.Filled.Call),
        EmergencyContact("Lost & Found", "+91-571-222-5555", "Support", Icons.Filled.Call)
    )

    val initiateCall: (String, String) -> Unit = { phoneNumber, contactName ->
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                context.startActivity(intent)
                lastCalledNumber = phoneNumber
                lastCalledName = contactName
                showDialog = true
            } catch (e: Exception) {
                println("Error initiating call: ${e.message}")
            }
        } else {
            println("CALL_PHONE permission required")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Emergency Contacts",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PrimaryBlue
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "Tap any contact to call immediately",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(contacts) { contact ->
                    ContactCard(
                        contact = contact,
                        onCall = { initiateCall(contact.phone, contact.name) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Calling Dialog
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            "📞 Calling",
                            fontWeight = FontWeight.Bold,
                            color = ErrorRed,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                lastCalledName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Text(
                                lastCalledNumber,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Gray
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("OK", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ContactCard(contact: EmergencyContact, onCall: () -> Unit) {
    val categoryColor = when (contact.category) {
        "Emergency" -> ErrorRed
        "Medical" -> Color(0xFF42A5F5)
        "Safety" -> SuccessGreen
        "Fire" -> Color(0xFFFF6F00)
        "Disaster" -> Color(0xFF7B1FA2)
        "Tourist" -> Color(0xFF0097A7)
        else -> PrimaryBlue
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCall),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        border = CardDefaults.outlinedCardBorder().copy(width = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Call Button
                Button(
                    onClick = onCall,
                    modifier = Modifier.size(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = categoryColor),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Icon(
                        Icons.Filled.Call,
                        contentDescription = "Call ${contact.name}",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        contact.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                    Text(
                        contact.phone,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }
            }

            // Category Badge
            Box(
                modifier = Modifier
                    .background(categoryColor, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    contact.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
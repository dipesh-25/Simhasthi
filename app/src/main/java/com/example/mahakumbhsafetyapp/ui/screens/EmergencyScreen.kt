package com.example.mahakumbhsafetyapp.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.ui.Routes
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.example.mahakumbhsafetyapp.ui.theme.SecondaryAccent
import com.example.mahakumbhsafetyapp.ui.theme.ErrorRed
import com.example.mahakumbhsafetyapp.ui.theme.SuccessGreen
import com.example.mahakumbhsafetyapp.ui.theme.DarkText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(navController: NavController) {
    val context = LocalContext.current

    val primaryBlue = PrimaryBlue
    val safetyGreen = SuccessGreen
    val errorRed = ErrorRed
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val backgroundColor = MaterialTheme.colorScheme.background

    // Function to check and initiate call
    val initiateCall: (String) -> Unit = { phoneNumber ->
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intent)
        } else {
            println("CALL_PHONE permission required to initiate direct call to $phoneNumber.")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Emergency SOS", color = onPrimaryColor, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryBlue),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = onPrimaryColor)
                    }
                }
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Press the button below for immediate help.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = DarkText,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // --- Primary Action: SOS Button ---
            Button(
                onClick = {
                    initiateCall("100")
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = errorRed),
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 12.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Call,
                        contentDescription = "SOS Call",
                        modifier = Modifier.size(64.dp),
                        tint = onPrimaryColor
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "SOS",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = onPrimaryColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            // --- Secondary Action: Safety Guide ---
            OutlinedButton(
                onClick = { navController.navigate(Routes.SAFETY_GUIDE) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(2.dp, safetyGreen, shape = RoundedCornerShape(4.dp))
            ) {
                Text("View Detailed Safety Guide", color = safetyGreen, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Tertiary Action: Full Contact List ---
            TextButton(
                onClick = { navController.navigate(Routes.EMERGENCY_CONTACTS) }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Info, contentDescription = "Contacts", modifier = Modifier.size(20.dp), tint = primaryBlue)
                    Spacer(Modifier.width(4.dp))
                    Text("Full List of Emergency Contacts", color = primaryBlue, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
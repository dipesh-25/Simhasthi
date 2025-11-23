package com.example.mahakumbhsafetyapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// --- THEME COLORS (YELLOW/WHITE) ---
private val PRIMARY_ACCENT = Color(0xFFFFC107) // Vibrant Amber/Yellow
private val LIGHT_BACKGROUND = Color(0xFFFFFFFF) // Pure White
private val LIGHT_SURFACE = Color(0xFFFFF8E1) // Light Yellow/Cream for cards/surfaces
private val DARK_TEXT = Color(0xFF333333) // Dark Gray for contrast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var notify by remember { mutableStateOf(true) }
    var location by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings", color = DARK_TEXT, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = LIGHT_BACKGROUND
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = DARK_TEXT)
                    }
                }
            )
        },
        containerColor = LIGHT_BACKGROUND
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // --- Preferences Section ---
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = LIGHT_SURFACE,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("App Preferences", color = PRIMARY_ACCENT, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    SettingToggleRow(
                        label = "Emergency Notifications",
                        checked = notify,
                        onCheckedChange = { notify = it }
                    )

                    Divider(color = DARK_TEXT.copy(alpha = 0.1f))

                    SettingToggleRow(
                        label = "Location Tracking",
                        checked = location,
                        onCheckedChange = { location = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Support Section ---
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = LIGHT_SURFACE,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Support & Information", color = PRIMARY_ACCENT, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    SettingClickableRow(label = "Privacy Policy")
                    Divider(color = DARK_TEXT.copy(alpha = 0.1f))
                    SettingClickableRow(label = "About SimhSaathi v1.0")
                }
            }
        }
    }
}

@Composable
fun SettingToggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp, horizontal = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = DARK_TEXT, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LIGHT_BACKGROUND,
                checkedTrackColor = PRIMARY_ACCENT,
                uncheckedThumbColor = LIGHT_BACKGROUND,
                uncheckedTrackColor = DARK_TEXT.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun SettingClickableRow(label: String, onClick: () -> Unit = {}) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = DARK_TEXT, fontSize = 16.sp, fontWeight = FontWeight.Normal)
        }
    }
}
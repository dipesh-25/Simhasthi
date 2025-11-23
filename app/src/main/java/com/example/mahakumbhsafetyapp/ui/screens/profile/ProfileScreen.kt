package com.example.mahakumbhsafetyapp.ui.screens.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private const val PREFS = "simhsaathi_prefs"
private const val KEY_USER = "user_email"
private const val KEY_LANGUAGE = "app_language" // true = English, false = Hindi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val ctx = LocalContext.current
    val prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    // Load saved data
    val username = prefs.getString(KEY_USER, "Pilgrim")?.substringBefore("@") ?: "Pilgrim" // Use name before @

    // Language state: true = English (Default), false = Hindi
    var isEnglish by remember { mutableStateOf(prefs.getBoolean(KEY_LANGUAGE, true)) }

    val primaryGold = Color(0xFFFFD54F)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile & Settings", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF040B11))
            )
        },
        containerColor = Color(0xFF040B11)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(Color(0xFF040B11)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // --- Profile Header ---
            Icon(
                Icons.Default.Person,
                contentDescription = "User Avatar",
                modifier = Modifier.size(72.dp),
                tint = primaryGold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Welcome, $username", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(30.dp))

            // --- Menu Section: Settings & Info ---
            ProfileCard(title = "General Settings", modifier = Modifier.fillMaxWidth()) {

                // Language Row (Localization Setup)
                ProfileToggleRow(
                    icon = Icons.Default.Language,
                    title = "App Language",
                    text = if (isEnglish) "A/अ" else "अ/A",
                    onClick = {
                        isEnglish = !isEnglish // Flip state
                        prefs.edit().putBoolean(KEY_LANGUAGE, isEnglish).apply()
                        // NOTE: You need to implement app-wide string resource loading logic that reads this preference.
                    },
                    isEnglish = isEnglish
                )
                Divider(color = Color.Black.copy(alpha = 0.3f))

                // Preferences/Settings Row
                ProfileRow(
                    icon = Icons.Default.Settings,
                    title = "App Preferences",
                    onClick = { navController.navigate("settings") }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // --- Logout Button ---
            Button(
                onClick = {
                    // Clear "remember me" flag and navigate to Login
                    prefs.edit().putBoolean("remember_me", false).apply()
                    navController.navigate("login") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF880000)), // Red for final action
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Out", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ProfileCard(title: String, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = modifier) {
        Text(title, color = Color(0xFFFFD54F), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2C38)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun ProfileRow(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color(0xFFFFD54F))
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, color = Color.White, fontSize = 16.sp)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
    }
}

@Composable
fun ProfileToggleRow(icon: ImageVector, title: String, text: String, onClick: () -> Unit, isEnglish: Boolean) {
    val primaryGold = Color(0xFFFFD54F)
    val buttonColor = if (isEnglish) primaryGold else Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = primaryGold)
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, color = Color.White, fontSize = 16.sp)
        }
        // Button that shows A or अ
        Button(
            onClick = onClick,
            modifier = Modifier.width(70.dp).height(36.dp),
            contentPadding = PaddingValues(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}
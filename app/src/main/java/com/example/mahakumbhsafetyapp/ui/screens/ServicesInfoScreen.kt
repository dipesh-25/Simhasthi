package com.example.mahakumbhsafetyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue // CRITICAL FIX: Import color
import com.example.mahakumbhsafetyapp.ui.theme.LightBackground // CRITICAL FIX: Import color
import com.example.mahakumbhsafetyapp.ui.Routes
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.example.mahakumbhsafetyapp.ui.theme.LightBackground
import androidx.compose.material.icons.filled.Call

/**
 * Placeholder for a utility screen that might show services like charging stations, restrooms, etc.
 * Referenced to resolve unresolved references in error log.
 */
@Composable
fun ServicesInfoScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Services Information (Restrooms, Charging) Placeholder",
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )
    }
}
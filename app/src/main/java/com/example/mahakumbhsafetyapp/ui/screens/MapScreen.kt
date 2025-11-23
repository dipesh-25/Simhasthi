package com.example.mahakumbhsafetyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

/**
 * Live Map Screen showing Simhasth Ujjain zones with visible circles
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    // Simhasth Ujjain location (Ujjain, Madhya Pradesh)
    val simhasthLocation = LatLng(23.1815, 75.7733)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(simhasthLocation, 15f)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Live Map & Zones",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        isMyLocationEnabled = false
                    )
                ) {
                    // GREEN ZONE - Safe Area
                    Circle(
                        center = LatLng(23.1800, 75.7700),
                        radius = 400.0,
                        fillColor = Color(0xFF66BB6A).copy(alpha = 0.3f),
                        strokeColor = Color(0xFF66BB6A),
                        strokeWidth = 3f
                    )

                    // YELLOW ZONE - Moderate Crowd
                    Circle(
                        center = LatLng(23.1830, 75.7760),
                        radius = 500.0,
                        fillColor = Color(0xFFFFB300).copy(alpha = 0.3f),
                        strokeColor = Color(0xFFFFB300),
                        strokeWidth = 3f
                    )

                    // RED ZONE - High Risk
                    Circle(
                        center = LatLng(23.1820, 75.7750),
                        radius = 350.0,
                        fillColor = Color(0xFFEF5350).copy(alpha = 0.3f),
                        strokeColor = Color(0xFFEF5350),
                        strokeWidth = 3f
                    )

                    // Markers with Info Windows
                    Marker(
                        state = MarkerState(position = LatLng(23.1800, 75.7700)),
                        title = "🟢 Green Zone - Safe",
                        snippet = "Low crowd density - Safe to visit",
                        tag = "green"
                    )

                    Marker(
                        state = MarkerState(position = LatLng(23.1830, 75.7760)),
                        title = "🟡 Yellow Zone - Moderate",
                        snippet = "Moderate crowd - Use caution",
                        tag = "yellow"
                    )

                    Marker(
                        state = MarkerState(position = LatLng(23.1820, 75.7750)),
                        title = "🔴 Red Zone - High Risk",
                        snippet = "High crowd - Avoid if possible",
                        tag = "red"
                    )

                    // Central Location
                    Marker(
                        state = MarkerState(position = LatLng(23.1815, 75.7733)),
                        title = "Simhasth Ujjain 2025",
                        snippet = "Main Event Location",
                        tag = "center"
                    )

                    // User Location
                    Marker(
                        state = MarkerState(position = LatLng(23.1825, 75.7740)),
                        title = "📍 Your Location",
                        snippet = "You are here",
                        tag = "user"
                    )
                }
            }

            // --- Zone Information Panel ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Simhasth Ujjain 2025",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )

                    Text(
                        "Zone Information",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )

                    ZoneIndicator(
                        color = Color(0xFF66BB6A),
                        label = "🟢 Green Zone",
                        description = "Safe - Low crowd density"
                    )

                    ZoneIndicator(
                        color = Color(0xFFFFB300),
                        label = "🟡 Yellow Zone",
                        description = "Moderate - Medium crowd"
                    )

                    ZoneIndicator(
                        color = Color(0xFFEF5350),
                        label = "🔴 Red Zone",
                        description = "High Risk - Dense crowd!"
                    )

                    Divider(modifier = Modifier.padding(vertical = 4.dp))

                    Text(
                        "📍 Location: Ujjain, Madhya Pradesh",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ZoneIndicator(color: Color, label: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = RoundedCornerShape(8.dp))
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                description,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}
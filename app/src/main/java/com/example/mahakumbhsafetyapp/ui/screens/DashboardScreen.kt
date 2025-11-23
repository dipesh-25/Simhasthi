package com.example.mahakumbhsafetyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.ui.Routes
import com.example.mahakumbhsafetyapp.ui.theme.DarkText
import com.example.mahakumbhsafetyapp.ui.theme.LightBackground
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.example.mahakumbhsafetyapp.ui.theme.SecondaryAccent
import com.google.firebase.auth.FirebaseAuth

data class DashboardItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }

    // Get real user data from Firebase
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userName = currentUser?.email?.substringBefore("@") ?: "User"
    val userEmail = currentUser?.email ?: "user@example.com"

    // Mock data for stats
    val userTraveledKm = 12.5
    val userZone = "Green" // Green, Yellow, Red
    val zoneColor = when (userZone) {
        "Green" -> Color(0xFF66BB6A)
        "Yellow" -> Color(0xFFFFB300)
        "Red" -> Color(0xFFEF5350)
        else -> Color.Gray
    }

    val items = listOf(
        DashboardItem(
            title = "Live Map & Zones",
            icon = Icons.Filled.Map,
            route = Routes.MAP_TAB,
            color = Color(0xFF42A5F5)
        ),
        DashboardItem(
            title = "Safety Guide",
            icon = Icons.Filled.SafetyCheck,
            route = Routes.SAFETY_GUIDE,
            color = Color(0xFF66BB6A)
        ),
        DashboardItem(
            title = "Lost & Found",
            icon = Icons.Filled.FindInPage,
            route = Routes.LOST_FOUND_TAB,
            color = Color(0xFFFFB300)
        ),
        DashboardItem(
            title = "Emergency Contacts",
            icon = Icons.Filled.Call,
            route = Routes.EMERGENCY_CONTACTS,
            color = Color(0xFF9C27B0)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
    ) {
        // --- Header with User Details ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryBlue)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // User Avatar
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "User",
                            modifier = Modifier.size(36.dp),
                            tint = PrimaryBlue
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            userName.capitalize(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            userEmail,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                // Three-dot menu
                Box {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = {
                                navController.navigate(Routes.SETTINGS)
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(Icons.Filled.Settings, contentDescription = "Settings")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate(Routes.LOGIN) {
                                    popUpTo(Routes.MAIN) { inclusive = true }
                                }
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
                            }
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // --- User Stats Section ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Distance Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Filled.DirectionsCar,
                            contentDescription = "Distance",
                            modifier = Modifier.size(28.dp),
                            tint = PrimaryBlue
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "${userTraveledKm} km",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )
                        Text(
                            "Travelled",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }

                // Zone Status Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = zoneColor.copy(alpha = 0.2f)),
                    elevation = CardDefaults.cardElevation(4.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        width = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(zoneColor)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            userZone,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = zoneColor
                        )
                        Text(
                            "Zone",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            // --- Red Zone Alert (if applicable) ---
            if (userZone == "Red") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEF5350).copy(alpha = 0.2f)),
                    border = CardDefaults.outlinedCardBorder().copy(
                        width = 2.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            contentDescription = "Alert",
                            modifier = Modifier.size(28.dp),
                            tint = Color(0xFFEF5350)
                        )
                        Text(
                            "High crowd density in your area. Stay alert and follow safety guidelines.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFEF5350),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // --- Grid of Features ---
            Text(
                "Quick Access",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DarkText,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(items) { item ->
                    DashboardTile(
                        item = item,
                        onClick = {
                            // Navigate to tab in MainScreen
                            when (item.route) {
                                Routes.MAP_TAB -> {
                                    navController.navigate(Routes.MAIN) {
                                        popUpTo(Routes.HOME_TAB)
                                    }
                                    // Send signal to switch to MAP tab
                                }
                                Routes.LOST_FOUND_TAB -> {
                                    navController.navigate(Routes.MAIN) {
                                        popUpTo(Routes.HOME_TAB)
                                    }
                                    // Send signal to switch to LOST_FOUND tab
                                }
                                Routes.SAFETY_GUIDE -> {
                                    navController.navigate(Routes.SAFETY_GUIDE)
                                }
                                Routes.EMERGENCY_CONTACTS -> {
                                    navController.navigate(Routes.EMERGENCY_CONTACTS)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardTile(item: DashboardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = item.color.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                item.icon,
                contentDescription = item.title,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                item.title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
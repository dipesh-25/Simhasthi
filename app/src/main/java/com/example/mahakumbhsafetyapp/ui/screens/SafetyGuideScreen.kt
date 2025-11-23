package com.example.mahakumbhsafetyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.ui.Routes
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.example.mahakumbhsafetyapp.ui.theme.LightBackground
import com.example.mahakumbhsafetyapp.ui.theme.DarkText
import com.example.mahakumbhsafetyapp.ui.theme.SuccessGreen

data class SafetyTip(
    val title: String,
    val description: String,
    val icon: ImageVector = Icons.Filled.Info
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafetyGuideScreen(navController: NavController) {
    val safetyTips = listOf(
        SafetyTip(
            title = "Stay Hydrated",
            description = "Drink water regularly, especially during crowded events. Keep electrolyte drinks handy.",
            icon = Icons.Filled.Info
        ),
        SafetyTip(
            title = "Know the Exits",
            description = "Always identify emergency exits and assembly points when you arrive at any location.",
            icon = Icons.Filled.Info
        ),
        SafetyTip(
            title = "Travel in Groups",
            description = "Never travel alone. Keep your group together and establish meeting points.",
            icon = Icons.Filled.Info
        ),
        SafetyTip(
            title = "Emergency Contacts",
            description = "Save important phone numbers. Keep your phone charged and accessible.",
            icon = Icons.Filled.Info
        ),
        SafetyTip(
            title = "Document Your Belongings",
            description = "Take photos of valuable items and keep them in safe places.",
            icon = Icons.Filled.Info
        ),
        SafetyTip(
            title = "First Aid Kit",
            description = "Carry a basic first aid kit with essential medications and bandages.",
            icon = Icons.Filled.Info
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Safety Guide",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = Color.White
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
        },
        containerColor = LightBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Essential Safety Tips",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(safetyTips) { tip ->
                SafetyTipCard(tip = tip)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate(Routes.EMERGENCY_CONTACTS) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        Icons.Filled.Call,
                        contentDescription = "Call",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Emergency Contacts",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun SafetyTipCard(tip: SafetyTip) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, PrimaryBlue, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Filled.Info,
                contentDescription = null,
                tint = SuccessGreen,
                modifier = Modifier.size(28.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = tip.title,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = tip.description,
                    color = DarkText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
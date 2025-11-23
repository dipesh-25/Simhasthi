package com.example.mahakumbhsafetyapp.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mahakumbhsafetyapp.R

@Composable
fun WelcomeScreen(navController: NavController) {

    // Smooth breathing animation on logo
    val infinite = rememberInfiniteTransition()
    val scale = infinite.animateFloat(
        initialValue = 0.97f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBC02D)) // SAME OLD YELLOW BACKGROUND
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Animated App Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale.value) // ANIMATION ONLY
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "SimhSaathi",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )

            // Tagline
            Text(
                text = "सेवा, सुरक्षा, समाधान",
                fontSize = 18.sp,
                color = Color(0xFF0D47A1),
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Buttons same as your old UI
            BlueButton("Enter Map") { navController.navigate("map") }
            Spacer(modifier = Modifier.height(16.dp))
            BlueButton("Lost & Found") { navController.navigate("lostfound") }
            Spacer(modifier = Modifier.height(16.dp))
            BlueButton("Emergency Help") { navController.navigate("emergency") }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun BlueButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)), // SAME BLUE
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Text(text, fontSize = 18.sp, color = Color.White)
    }
}

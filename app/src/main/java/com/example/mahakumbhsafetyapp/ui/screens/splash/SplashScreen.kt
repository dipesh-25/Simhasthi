package com.example.mahakumbhsafetyapp.ui.screens.splash

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mahakumbhsafetyapp.R
import com.example.mahakumbhsafetyapp.ui.Routes
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

private const val PREFS = "mahakumbh_prefs"
private const val KEY_REMEMBER = "remember_me"

@Composable
fun SplashScreen(navController: NavController) {
    val ctx = LocalContext.current
    val prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    val auth = FirebaseAuth.getInstance()

    val currentUser = auth.currentUser
    val shouldRemember = prefs.getBoolean(KEY_REMEMBER, false)

    val startDestination = when {
        currentUser != null && shouldRemember -> Routes.MAIN
        currentUser != null -> Routes.MAIN
        else -> Routes.LOGIN
    }

    val infinite = rememberInfiniteTransition(label = "logo_pulse_transition")
    val scale by infinite.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_scale_anim"
    )

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(startDestination) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFBC02D), Color(0xFFF9A825))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // App Logo with Animation
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.logo)
                        .crossfade(true)
                        .build(),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(140.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Simhasthi Ujjain",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "सेवा • सुरक्षा • समाधान",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0D47A1)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "2025",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0D47A1)
            )
        }
    }
}
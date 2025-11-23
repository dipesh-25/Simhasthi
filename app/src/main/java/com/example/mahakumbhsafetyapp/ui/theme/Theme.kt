package com.example.mahakumbhsafetyapp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme

// --- Custom Colors (Fixes all Unresolved Color references) ---
val PrimaryBlue = Color(0xFF0D47A1) // Deep, rich blue (like the sky at the event)
val SecondaryAccent = Color(0xFFFFC107) // Vibrant Amber/Yellow (Holy color)
val ErrorRed = Color(0xFFD32F2F) // Standard danger color
val SuccessGreen = Color(0xFF388E3C) // Standard success/safety color
val LightBackground = Color(0xFFF5F5F5) // Very light gray background
val DarkText = Color(0xFF212121) // Near black text for high contrast

// --- Color Schemes ---

// Default Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    secondary = SecondaryAccent,
    tertiary = SuccessGreen, // Adding SuccessGreen to tertiary for screens that use it.
    error = ErrorRed,
    background = LightBackground,
    surface = Color.White,
    onSurface = DarkText,
    onSecondary = DarkText
)

// Default Dark Color Scheme (optional, but good practice)
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    secondary = SecondaryAccent,
    tertiary = SuccessGreen,
    error = ErrorRed,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    onSecondary = DarkText
)

@Composable
fun MahaKumbhSafetyAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
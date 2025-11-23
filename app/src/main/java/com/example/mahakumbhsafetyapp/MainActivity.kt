package com.example.mahakumbhsafetyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mahakumbhsafetyapp.ui.navigation.NavGraph
import com.example.mahakumbhsafetyapp.ui.theme.MahaKumbhSafetyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MahaKumbhSafetyAppTheme {
                AppNavigation()
            }
        }
    }
}

/**
 * Main navigation entry point for the entire application.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavGraph(navController)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MahaKumbhSafetyAppTheme {
        AppNavigation()
    }
}
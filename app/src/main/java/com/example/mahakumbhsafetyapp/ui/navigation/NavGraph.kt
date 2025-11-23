package com.example.mahakumbhsafetyapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mahakumbhsafetyapp.ui.Routes
import com.example.mahakumbhsafetyapp.ui.screens.*
import com.example.mahakumbhsafetyapp.ui.screens.login.LoginScreen
import com.example.mahakumbhsafetyapp.ui.screens.signup.SignupScreen
import com.example.mahakumbhsafetyapp.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        // Splash Screen
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        // Authentication Screens
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.SIGNUP) {
            SignupScreen(navController)
        }

        // Main App Entry Point (Bottom Navigation Host)
        composable(Routes.MAIN) {
            MainScreen(navController)
        }

        // Top-Level Utility Screens
        composable(Routes.SETTINGS) {
            SettingsScreen(navController)
        }

        composable(Routes.SAFETY_GUIDE) {
            SafetyGuideScreen(navController)
        }

        composable(Routes.EMERGENCY_CONTACTS) {
            EmergencyContactsScreen(navController)
        }
    }
}
package com.example.mahakumbhsafetyapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mahakumbhsafetyapp.ui.Routes
import com.example.mahakumbhsafetyapp.ui.theme.PrimaryBlue
import com.example.mahakumbhsafetyapp.ui.theme.SecondaryAccent

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem(Routes.HOME_TAB, "Home", Icons.Filled.Home)
    object Map : BottomNavItem(Routes.MAP_TAB, "Map", Icons.Filled.Map)
    object LostFound : BottomNavItem(Routes.LOST_FOUND_TAB, "Lost/Found", Icons.Filled.FindInPage)
    object EmergencyContacts : BottomNavItem(Routes.EMERGENCY_TAB, "Emergency", Icons.Filled.Call)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainNavController: NavController) {
    val tabNavController = rememberNavController()
    val navItems = listOf(BottomNavItem.Home, BottomNavItem.Map, BottomNavItem.LostFound, BottomNavItem.EmergencyContacts)
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Listen for deep link arguments to switch tabs
    LaunchedEffect(Unit) {
        mainNavController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("targetTab")?.observeForever { tab ->
            if (tab != null) {
                tabNavController.navigate(tab)
                mainNavController.currentBackStackEntry?.savedStateHandle?.remove<String>("targetTab")
            }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = PrimaryBlue,
                tonalElevation = 5.dp
            ) {
                navItems.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text(item.label) },
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != item.route) {
                                tabNavController.navigate(item.route) {
                                    tabNavController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) { saveState = true }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SecondaryAccent,
                            selectedTextColor = SecondaryAccent,
                            unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            indicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = tabNavController,
            startDestination = Routes.HOME_TAB,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.HOME_TAB) {
                DashboardScreen(mainNavController)
            }
            composable(Routes.MAP_TAB) {
                MapScreen(tabNavController)
            }
            composable(Routes.LOST_FOUND_TAB) {
                LostFoundScreen(tabNavController)
            }
            composable(Routes.EMERGENCY_TAB) {
                EmergencyContactsScreen(tabNavController)
            }
        }
    }
}
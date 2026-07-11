package com.team.moviefinder.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.team.moviefinder.ui.theme.LightBlue

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String? = null
) {
    val colorScheme = MaterialTheme.colorScheme

    // Если текущий маршрут не передан, берём из навигации
    val backStackEntry = navController.currentBackStackEntryAsState()
    val route = currentRoute ?: backStackEntry.value?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp),
        containerColor = colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        // главная
        NavigationBarItem(
            selected = route == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Главная",
                )
            },
            label = {
                Text("Главная")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LightBlue,
                selectedTextColor = LightBlue,
                unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )

        // поиск
        NavigationBarItem(
            selected = route == "search",
            onClick = {
                navController.navigate("search") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Поиск"
                )
            },
            label = {
                Text("Поиск")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LightBlue,
                selectedTextColor = LightBlue,
                unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )

        // настройки
        NavigationBarItem(
            selected = route == "settings",
            onClick = {
                navController.navigate("settings") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Настройки"
                )
            },
            label = {
                Text("Настройки")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = LightBlue,
                selectedTextColor = LightBlue,
                unselectedIconColor = colorScheme.onSurface.copy(alpha = 0.6f),
                unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )
    }
}
package com.team.moviefinder.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.team.moviefinder.ui.theme.LightBlue

@Composable
fun BottomNavBar(navController: NavController) {
    val route = navController.currentDestination?.route

    val colorScheme = MaterialTheme.colorScheme

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
        ,
        containerColor = colorScheme.surface,
    ) {
        NavigationBarItem(
            selected = route == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") {
                        saveState = true
                    }

                    launchSingleTop = true
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

                indicatorColor = Color.Transparent,
            ),
        )

        NavigationBarItem(
            selected = route == "search",
            onClick = {
                navController.navigate("search") {
                    popUpTo("search")

                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Поиск",
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

                indicatorColor = Color.Transparent,
            ),
        )

        NavigationBarItem(
            selected = route == "settings",
            onClick = {
                navController.navigate("settings") {
                    popUpTo("settings") {
                        saveState = true
                    }

                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Настройки",
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

                indicatorColor = Color.Transparent,
            ),
        )
    }
}
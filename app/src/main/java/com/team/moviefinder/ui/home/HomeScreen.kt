package com.team.moviefinder.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team.moviefinder.ui.theme.LightBlue

@Composable
fun HomeScreen() {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp)
                ,
                containerColor = colorScheme.surface,
                tonalElevation = 0.dp,
                content = {
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
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
                        ),
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {},
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
                        ),
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {},
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
                        ),
                    )
                }
            )
        },

        content = { padding: PaddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "MovieFinder",
                    color = colorScheme.onSurface,
                    style = typography.headlineLarge,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Приложение для поиска фильмов",
                    color = colorScheme.onSurface.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    style = typography.headlineMedium,
                )
            }
        },
    )
}
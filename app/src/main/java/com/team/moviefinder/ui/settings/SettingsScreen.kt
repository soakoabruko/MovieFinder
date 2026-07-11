package com.team.moviefinder.ui.settings

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.team.moviefinder.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }

    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Настройки",
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                },

                navigationIcon = {
                    IconButton(onClick = { /* navController.navigateUp() */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = colorScheme.onSurface,
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.surface,
                ),
            )
        },

        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp),
                containerColor = colorScheme.surface,
                tonalElevation = 0.dp,
                content = {
                    NavigationBarItem(
                        selected = false,
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
                        selected = true,
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
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Приложение для поиска фильмов",
                    color = colorScheme.onSurface.copy(alpha = 0.6f),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        showBottomSheet = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.surfaceVariant,
                    ),
                ) {
                    Text(
                        text = "О нашей команде",
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, "https://kpfu.ru".toUri())

                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.surfaceVariant,
                    ),
                ) {
                    Text(
                        text = "Сайт университета",
                        color = colorScheme.onSurface.copy(alpha = 0.6f),
                    )
                }

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                            ,
                        ) {
                            Text(
                                text = "О нашей команде",
                                modifier = Modifier.fillMaxWidth(),
                                color = colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                            )

                            Text(
                                text = "@soakoo",
                                color = colorScheme.onSurface.copy(alpha = 0.6f),
                            )

                            Text(
                                text = "@Yaroslave_Sav",
                                color = colorScheme.onSurface.copy(alpha = 0.6f),
                            )

                            Text(
                                text = "@tipookie",
                                color = colorScheme.onSurface.copy(alpha = 0.6f),
                            )

                            Text(
                                text = "@Souzn1k3",
                                color = colorScheme.onSurface.copy(alpha = 0.6f),
                            )
                        }
                    }
                }
            }
        }
    )
}
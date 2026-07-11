package com.team.moviefinder.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.team.moviefinder.ui.navigation.BottomNavBar

@Composable
fun HomeScreen(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        bottomBar = { BottomNavBar(navController) },

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
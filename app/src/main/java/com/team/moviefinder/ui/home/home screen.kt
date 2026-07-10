package com.team.moviefinder.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Movie Finder")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Приложение для поиска фильмов и просмотра информации о них.")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSearchClick
        ) {
            Text("Поиск фильмов")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onSettingsClick
        ) {
            Text("Настройки")
        }
    }
}
package com.team.moviefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import com.team.moviefinder.data.repository.TestViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.Text
import com.team.moviefinder.ui.search.SearchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                // расположение под status bar-ом
                content = { padding: PaddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                    ) {
                        // содержимое приложения
                        // TestMovieScreen()

                        // TestMovieResponseScreen()

                        SearchScreen()
                    }
                }
            )
        }
    }
}

@Composable
fun TestMovieScreen(
    vm: TestViewModel = viewModel()
) {
    val state by vm.state

    LaunchedEffect(Unit) {
        vm.getMovieById(84049)
    }

    if (state.isLoading) {
        Text("Загрузка")
    } else if (state.error != null) {
        Text("Failed to load: ${state.error}")
    } else if (state.selectedMovie != null) {
        Text(state.selectedMovie.toString())
    }
}

@Composable
fun TestMovieResponseScreen(
    vm: TestViewModel = viewModel()
) {
    val state by vm.state

    LaunchedEffect(Unit) {
        vm.searchMovieByKeyword("Рапунцель")
    }

    if (state.isLoading) {
        Text("Загрузка")
    } else if (state.error != null) {
        Text("Failed to load: ${state.error}")
    } else if (state.searchResult != null) {
        Text(state.searchResult.toString())
    }
}
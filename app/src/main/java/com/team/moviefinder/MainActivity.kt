package com.team.moviefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.team.moviefinder.data.repository.TestViewModel
import com.team.moviefinder.ui.navigation.AppNavGraph
import com.team.moviefinder.ui.theme.MovieFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieFinderTheme {
                AppNavGraph()
            }
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
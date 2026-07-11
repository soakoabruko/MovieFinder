package com.team.moviefinder

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import com.team.moviefinder.ui.theme.MovieFinderTheme
import com.team.moviefinder.ui.details.DetailsScreen
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
            MovieFinderTheme {
                // содержимое приложения
                // TestMovieScreen()

                // TestMovieResponseScreen()

                DetailsScreen(301)
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
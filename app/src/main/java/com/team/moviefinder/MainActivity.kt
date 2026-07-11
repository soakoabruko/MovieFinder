package com.team.moviefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import com.team.moviefinder.ui.theme.MovieFinderTheme
import androidx.compose.runtime.Composable
import com.team.moviefinder.data.repository.MovieFinderApiViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.Text
import com.team.moviefinder.ui.home.HomeScreen
import com.team.moviefinder.ui.settings.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieFinderTheme {
                //TestMovieScreen()

                // TestMovieResponseScreen()

                HomeScreen()

                // SettingsScreen()
            }
        }
    }
}

@Composable
fun TestMovieScreen(
    vm: MovieFinderApiViewModel = viewModel()
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
    vm: MovieFinderApiViewModel = viewModel()
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
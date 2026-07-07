package com.team.moviefinder

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import com.team.moviefinder.data.repository.MovieFinderApiViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.Text

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
                            .background(Color.DarkGray)
                            .fillMaxSize()
                            .padding(padding)
                        ,
                    ) {
                        // содержимое приложения
                        TestMovieScreen()

                        // TestMovieResponseScreen()
                    }
                }
            )
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
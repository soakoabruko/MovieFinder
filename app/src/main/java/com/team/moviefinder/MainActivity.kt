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
import androidx.compose.runtime.LaunchedEffect
import com.team.moviefinder.data.repository.UiState
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                content = { padding: PaddingValues -> // расположение под status bar-ом
                    Column(
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .fillMaxSize()
                            .padding(padding)
                        ,
                    ) {
                        // содержимое приложения
                        // TestMovieScreen()

                        TestMovieResponseScreen()
                    }
                }
            )
        }
    }
}

// примеры использования api (можно удалить)

@Composable
fun TestMovieScreen(
    vm: MovieFinderApiViewModel = viewModel()
) {
    LaunchedEffect(Unit) { // корутина при первом вызове TestScreen
        vm.getMovieById(263531)
    }

    val uiState = vm.uiState.value

    when (uiState) {
        is UiState.Loading -> Text("Загрузка")
        is UiState.Success -> Text(uiState.data.toString())
        is UiState.Error -> Text(uiState.message)
    }
}

@Composable
fun TestMovieResponseScreen(
    vm: MovieFinderApiViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        vm.searchMovieByKeyword("Мстители")
    }

    val uiState = vm.uiState.value

    when (uiState) {
        is UiState.Loading -> Text("Загрузка")
        is UiState.Success -> Text(uiState.data.toString())
        is UiState.Error -> Text(uiState.message)
    }
}
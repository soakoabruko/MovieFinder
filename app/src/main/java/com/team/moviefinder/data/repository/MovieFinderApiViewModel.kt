package com.team.moviefinder.data.repository

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieFinderApiViewModel(): ViewModel() {
    private val internalState: MutableState<UiState<Any>> = mutableStateOf(UiState.Loading)
    val uiState: State<UiState<Any>> = internalState

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            internalState.value = UiState.Loading

            try {
                val movie = MovieFinderApiRepository.getMovieById(id)
                internalState.value = UiState.Success(movie)
            } catch (e: Exception) {
                internalState.value = UiState.Error("Failed to load: ${e.message}")
            }
        }
    }

    fun searchMovieByKeyword(
        keyword: String,
        page: Int = 1,
    ) {
        viewModelScope.launch {
            internalState.value = UiState.Loading

            try {
                val movieResponse = MovieFinderApiRepository.searchMovieByKeywordAndPage(keyword, page)
                internalState.value = UiState.Success(movieResponse)
            } catch (e: Exception) {
                internalState.value = UiState.Error("Failed to load: ${e.message}")
            }
        }
    }
}
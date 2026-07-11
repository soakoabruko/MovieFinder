package com.team.moviefinder.ui.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.team.moviefinder.data.repository.MovieFinderApiRepository

class DetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading

            try {
                val response = MovieFinderApiRepository.getMovieById(movieId)
                _uiState.value = DetailsUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

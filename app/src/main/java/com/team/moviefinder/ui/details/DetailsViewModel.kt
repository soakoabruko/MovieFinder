package com.team.moviefinder.ui.details

import androidx.lifecycle.*
import com.team.moviefinder.data.repository.MovieFinderApiRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

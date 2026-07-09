package com.team.moviefinder.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.moviefinder.data.models.MovieDetailsResponse
import com.team.moviefinder.data.repository.MovieFinderApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Состояния экрана с реальной моделью
sealed class DetailsUiState {
    object Loading : DetailsUiState()
    data class Success(val movie: MovieDetailsResponse) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}

class DetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            try {
                // Используем настоящий репозиторий (он сделан как singleton object)
                val movie = MovieFinderApiRepository.getMovieById(movieId)
                _uiState.value = DetailsUiState.Success(movie)
            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error(e.message ?: "Произошла неизвестная ошибка при загрузке сети")
            }
        }
    }
}

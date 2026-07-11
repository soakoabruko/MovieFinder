package com.team.moviefinder.ui.details

import com.team.moviefinder.data.models.MovieDetailsResponse

// cостояния экрана
sealed class DetailsUiState {
    object Loading : DetailsUiState()
    data class Success(val film: MovieDetailsResponse) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}
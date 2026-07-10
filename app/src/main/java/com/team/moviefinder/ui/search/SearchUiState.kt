package com.team.moviefinder.ui.search

import com.team.moviefinder.data.models.MovieSearchItemResponse

// состояния экрана поиска
sealed class SearchUiState {
    object Idle: SearchUiState()
    object Loading: SearchUiState()
    data class Success(
        val totalFilms: Int,
        val allFilms: List<MovieSearchItemResponse>,
    ): SearchUiState()
    data class Error(val message: String): SearchUiState()
    object Empty : SearchUiState()
}
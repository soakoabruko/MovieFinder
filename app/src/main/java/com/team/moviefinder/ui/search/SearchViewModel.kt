package com.team.moviefinder.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.moviefinder.data.models.MovieSearchItemResponse
import com.team.moviefinder.data.repository.MovieFinderApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel для экрана поиска
class SearchViewModel: ViewModel() {
    // состояние экрана поиска
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    // свойства текущей страницы
    private var currentQuery = ""
    private var currentPage = 1
    private var totalFilms = 0
    private val allFilms = mutableListOf<MovieSearchItemResponse>()

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Idle
            return
        }

        // Сбрасываем состояние при новом поиске
        currentQuery = query
        currentPage = 1
        allFilms.clear()

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            try {
                val response = MovieFinderApiRepository.searchMovieByKeywordAndPage(query, currentPage)

                if (response.searchFilmsCountResult == 0) {
                    _uiState.value = SearchUiState.Empty
                } else {
                    totalFilms = response.searchFilmsCountResult
                    allFilms.addAll(response.films)
                    _uiState.value = SearchUiState.Success(
                        totalFilms = totalFilms,
                        allFilms = allFilms.toList(),
                    )
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadMore() {
        currentPage++

        viewModelScope.launch {
            try {
                val response = MovieFinderApiRepository.searchMovieByKeywordAndPage(currentQuery, currentPage)

                allFilms.addAll(response.films)
                _uiState.value = SearchUiState.Success(
                    totalFilms = totalFilms,
                    allFilms = allFilms.toList(),
                )
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
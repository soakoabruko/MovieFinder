package com.team.moviefinder.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.moviefinder.data.models.MovieSearchResponse
import com.team.moviefinder.data.repository.MovieFinderApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// состояния экрана поиска
sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val data: MovieSearchResponse) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
    object Empty : SearchUiState()
}

// ViewModel для экрана поиска
class SearchViewModel : ViewModel() {
    // состояние экрана
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    // параметры текущей страницы
    private var currentPage = 1
    private var currentQuery = ""
    private var totalFilms = 0
    private val allFilms = mutableListOf<com.team.moviefinder.data.models.MovieSearchItemResponse>()

    // поиск фильмов по переданным данным
    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Idle
            return
        }

        // Сбрасываем состояние при новом поиске
        currentPage = 1
        currentQuery = query
        allFilms.clear()
        totalFilms = 0

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            try {
                val response = MovieFinderApiRepository.searchMovieByKeywordAndPage(query, currentPage)
                totalFilms = response.searchFilmsCountResult
                allFilms.addAll(response.films)

                if (allFilms.isEmpty()) {
                    _uiState.value = SearchUiState.Empty
                } else {
                    _uiState.value = SearchUiState.Success(
                        MovieSearchResponse(
                            pagesCount = response.pagesCount,
                            searchFilmsCountResult = totalFilms,
                            films = allFilms.toList()
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    // загрузка следующей страницы
    fun loadMore() {
        // Проверяем, есть ли ещё страницы
        if (allFilms.size >= totalFilms) {
            println("Все фильмы уже загружены")
            return
        }

        val nextPage = currentPage + 1
        viewModelScope.launch {
            try {
                val response = MovieFinderApiRepository.searchMovieByKeywordAndPage(currentQuery, nextPage)
                currentPage = nextPage
                allFilms.addAll(response.films)

                _uiState.value = SearchUiState.Success(
                    MovieSearchResponse(
                        pagesCount = response.pagesCount,
                        searchFilmsCountResult = totalFilms,
                        films = allFilms.toList()
                    )
                )
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    // возврат состояния к начальному
    fun resetState() {
        _uiState.value = SearchUiState.Idle
        allFilms.clear()
        currentPage = 1
        totalFilms = 0
    }
}
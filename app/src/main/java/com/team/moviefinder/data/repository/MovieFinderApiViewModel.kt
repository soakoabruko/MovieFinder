package com.team.moviefinder.data.repository

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieFinderApiViewModel: ViewModel() {
    private val _state = mutableStateOf(MovieScreenState())
    val state: State<MovieScreenState> = _state

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val response = MovieFinderApiRepository.getMovieById(id)
                _state.value = _state.value.copy(
                    isLoading = false,
                    selectedMovie = response,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun searchMovieByKeyword(
        keyword: String,
        page: Int = 1,
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val response = MovieFinderApiRepository.searchMovieByKeywordAndPage(keyword, page)
                _state.value = _state.value.copy(
                    isLoading = false,
                    searchResult = response,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
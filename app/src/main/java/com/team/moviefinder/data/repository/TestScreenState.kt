package com.team.moviefinder.data.repository

import com.team.moviefinder.data.models.MovieDetailsResponse
import com.team.moviefinder.data.models.MovieSearchResponse

data class TestScreenState (
    val isLoading: Boolean = false,
    val searchResult: MovieSearchResponse? = null,
    val selectedMovie: MovieDetailsResponse? = null,
    var error: String? = null,
)
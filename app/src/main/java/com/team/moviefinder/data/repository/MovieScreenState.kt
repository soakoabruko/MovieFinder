package com.team.moviefinder.data.repository

import com.team.moviefinder.data.models.MovieSearchResponse
import com.team.moviefinder.data.models.MovieDetailsResponse

data class MovieScreenState (
    val isLoading: Boolean = false,
    val searchResult: MovieSearchResponse? = null,
    val selectedMovie: MovieDetailsResponse? = null,
    var error: String? = null,
)
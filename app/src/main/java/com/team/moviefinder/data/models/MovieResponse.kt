package com.team.moviefinder.data.models

data class MovieResponse (
    val pagesCount: Int,
    val searchFilmsCountResult: Int,
    val films: List<Movie>,
)
package com.team.moviefinder.data.models

import com.google.gson.annotations.SerializedName

// Модель ответа с количеством найденных фильмов и их список

// filmsCount - кол-во найденных фильмов
// films - список фильмов

data class MovieResponse(
    @SerializedName("searchFilmsCountResult")
    val filmsCount: Int,

    @SerializedName("films")
    val films: List<Movie>
)
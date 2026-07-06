package com.team.moviefinder.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("kinopoiskId")
    val filmId: Int,

    val nameRu: String?,
    val nameOriginal: String?,
    val posterUrl: String,

    @SerializedName("ratingKinopoisk")
    val rating: Float?,

    val description: String?,
)
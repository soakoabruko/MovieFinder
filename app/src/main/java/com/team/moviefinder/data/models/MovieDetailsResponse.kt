package com.team.moviefinder.data.models

import com.google.gson.annotations.SerializedName

/**
 * Модель данных ответа с детальной информацией о фильме.
 *
 * @property filmId Идентификатор фильма.
 * @property nameRu Название фильма на русском.
 * @property nameEn Название фильма на английском.
 * @property posterUrl URL-адрес постера фильма.
 * @property rating Рейтинг фильма на Кинопоиске.
 * @property description Описание фильма.
 */
data class MovieDetailsResponse(
    @SerializedName("kinopoiskId")
    val filmId: Int,
    @SerializedName("nameRu")
    val nameRu: String?,
    @SerializedName("nameEn")
    val nameEn: String?,
    @SerializedName("posterUrl")
    val posterUrl: String,
    @SerializedName("ratingKinopoisk")
    val rating: Float?,
    @SerializedName("description")
    val description: String?,
)
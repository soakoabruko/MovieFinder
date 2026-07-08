package com.team.moviefinder.data.models

import com.google.gson.annotations.SerializedName

/**
 * Модель данных ответа на поиск фильмов.
 *
 * @property pagesCount Общее количество страниц с результатами поиска.
 * @property searchFilmsCountResult Общее количество найденных фильмов.
 * @property films Список фильмов, найденных по запросу
 */
data class MovieSearchResponse (
    @SerializedName("pagesCount")
    val pagesCount: Int,
    @SerializedName("searchFilmsCountResult")
    val searchFilmsCountResult: Int,
    @SerializedName("films")
    val films: List<MovieSearchItemResponse>,
)

/**
 * Модель данных элемента списка фильмов в результатах поиска.
 *
 * @property filmId Идентификатор фильма.
 * @property nameRu Название фильма на русском языке.
 * @property nameEn Название фильма на английском языке.
 * @property posterUrl URL-адрес постера фильма.
 */
data class MovieSearchItemResponse (
    @SerializedName("filmId")
    val filmId: Int,
    @SerializedName("nameRu")
    val nameRu: String,
    @SerializedName("nameEn")
    val nameEn: String,
    @SerializedName("posterUrl")
    val posterUrl: String,

    @SerializedName("rating")
    val rating: String? = null, // !!! не Double, а String приходит с API
)
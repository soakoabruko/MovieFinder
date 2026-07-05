package com.team.moviefinder.data.models

import com.google.gson.annotations.SerializedName

// Модель данных фильма

// filmId - Id фильма
// title - Название фильма
// description - Описание фильма
// rating - Рейтинг фильма
// poster - Ссылка на постер

data class Movie(
    @SerializedName("filmId")
    val filmId: Int,

    @SerializedName("nameRu")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("rating")
    val rating: String, // !!! с api рейтинг приходит как String

    @SerializedName("posterUrl")
    val poster: String
)
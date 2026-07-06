package com.team.moviefinder.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import com.team.moviefinder.data.models.Movie
import retrofit2.http.Query
import com.team.moviefinder.data.models.MovieResponse

interface MovieFinderApi {
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int,
    ): Movie // suspend функция не блокирует текущий поток

    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchMovieByKeywordAndPage(
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
    ): MovieResponse
}
package com.team.moviefinder.data.repository

import com.team.moviefinder.data.models.Movie
import com.team.moviefinder.data.models.MovieResponse

object MovieFinderApiRepository {
    suspend fun getMovieById(id: Int): Movie {
        return RetrofitInstance.api.getMovieById(id)
    }

    suspend fun searchMovieByKeywordAndPage(
        keyword: String,
        page: Int,
    ): MovieResponse {
        return RetrofitInstance.api.searchMovieByKeywordAndPage(keyword, page)
    }
}
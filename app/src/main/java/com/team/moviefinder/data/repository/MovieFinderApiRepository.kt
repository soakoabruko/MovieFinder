package com.team.moviefinder.data.repository

import com.team.moviefinder.data.models.MovieDetailsResponse
import com.team.moviefinder.data.models.MovieSearchResponse

object MovieFinderApiRepository {
    suspend fun getMovieById(id: Int): MovieDetailsResponse {
        return RetrofitInstance.api.getMovieById(id)
    }

    suspend fun searchMovieByKeywordAndPage(
        keyword: String,
        page: Int,
    ): MovieSearchResponse {
        return RetrofitInstance.api.searchMovieByKeywordAndPage(keyword, page)
    }
}
package com.team.moviefinder.data.repository

import com.team.moviefinder.BuildConfig
import okhttp3.OkHttpClient
import com.team.moviefinder.data.api.MovieFinderApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    private const val API_KEY = BuildConfig.API_KEY

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain -> // добавление перехватчика
                chain.proceed( // обработка запроса
                    chain.request().newBuilder() // копия запроса
                        .addHeader("X-API-KEY", API_KEY)
                        .addHeader("Content-Type", "application/json")
                        .build()
                )
            }
            .build()
    }

    val api: MovieFinderApi by lazy { // ленивая инициализация
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieFinderApi::class.java)
    }
}
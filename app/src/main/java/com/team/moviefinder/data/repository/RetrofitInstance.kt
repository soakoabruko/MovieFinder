package com.team.moviefinder.data.repository

import com.team.moviefinder.BuildConfig
import com.team.moviefinder.data.api.MovieFinderApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val API_KEY = BuildConfig.API_KEY

    /**
     * HTTP-клиент для выполнения запросов к API.
     *
     * Все исходящие запросы автоматически дополняются обязательными
     * заголовками авторизации и формата данных.
     */
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            // Перехватчик позволяет изменить запрос перед его отправкой
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        // API-ключ для аутентификации запросов
                        .addHeader("X-API-KEY", API_KEY)
                        .build()
                )
            }
            // Передача модифицированного запроса дальше по цепочке
            .build()
    }

    val api: MovieFinderApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieFinderApi::class.java)
    }
}
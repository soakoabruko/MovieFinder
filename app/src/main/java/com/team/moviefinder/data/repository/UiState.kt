package com.team.moviefinder.data.repository

sealed class UiState<out T> { // T только для чтения
    object Loading: UiState<Nothing>() // нет данных

    data class Success<T>(val data: T): UiState<T>() // есть данные типа T

    data class Error(val message: String): UiState<Nothing>() // нет данных
}
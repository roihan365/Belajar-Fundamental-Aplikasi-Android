package com.dicoding.newsapp.data
import com.dicoding.newsapp.data.Result

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String): Result<Nothing>()
    object Loading : com.dicoding.newsapp.data.Result<Nothing>()
}
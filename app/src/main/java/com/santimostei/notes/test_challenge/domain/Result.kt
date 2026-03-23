package com.santimostei.notes.test_challenge.domain

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data object Loading : Result<Nothing>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
}
package com.coldandroid.cinema.domain.entity

sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val error: String? = null): Result<Nothing>()
}
package com.coldandroid.cinema.data.entity

data class ResponseTopRated(
    val results: ArrayList<Result>
)

data class Result(
    val title: String,
    val overview: String,
    val poster_path: String
)
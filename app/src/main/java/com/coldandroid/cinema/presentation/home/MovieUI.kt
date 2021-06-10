package com.coldandroid.cinema.presentation.home

import com.coldandroid.cinema.domain.entity.Movie

data class MovieUI(
    val title: String,
    val description: String,
    val posterUrl: String?
)

fun mapMovieToMovieUI(movie: Movie) : MovieUI {
    return MovieUI(
        movie.title ?: "(unknown)",
        movie.description ?: "",
        movie.posterUrl ?: "")
}
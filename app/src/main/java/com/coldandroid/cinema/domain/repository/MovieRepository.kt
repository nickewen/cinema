package com.coldandroid.cinema.domain.repository

import com.coldandroid.cinema.domain.entity.Movie
import com.coldandroid.cinema.domain.entity.Result

interface MovieRepository {
    suspend fun getTopRatedMovies(): Result<List<Movie>>
}
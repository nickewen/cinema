package com.coldandroid.cinema.domain.usecase.getmovies

import com.coldandroid.cinema.domain.entity.Movie
import com.coldandroid.cinema.domain.entity.Result
import com.coldandroid.cinema.domain.repository.MovieRepository
import com.coldandroid.cinema.domain.usecase.network.IsConnectedUseCase

interface ExploreMoviesUseCase {
    suspend fun getMovies(): Result<List<Movie>>
}

class ExploreMoviesUseCaseImpl(
    private val isConnectedUseCase: IsConnectedUseCase,
    private val movieRepository: MovieRepository
) : ExploreMoviesUseCase {
    override suspend fun getMovies(): Result<List<Movie>> =
        if (isConnectedUseCase.isConnected()) {
            movieRepository.getTopRatedMovies()
        } else {
            Result.Error()
        }
}
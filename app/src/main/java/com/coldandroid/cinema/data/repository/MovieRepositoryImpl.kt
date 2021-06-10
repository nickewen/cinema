package com.coldandroid.cinema.data.repository

import androidx.annotation.VisibleForTesting
import com.coldandroid.cinema.data.datasource.TMDBDataSource
import com.coldandroid.cinema.data.entity.ResponseTopRated
import com.coldandroid.cinema.domain.entity.Movie
import com.coldandroid.cinema.domain.entity.Result
import com.coldandroid.cinema.domain.repository.MovieRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MovieRepositoryImpl(private val coroutineContext: CoroutineContext, private val movieDataSource: TMDBDataSource) : MovieRepository {
    override suspend fun getTopRatedMovies(): Result<List<Movie>> = withContext(coroutineContext) {
        try {
            Result.Success(mapTopRatedMovies(movieDataSource.getTopRatedMovies()))
        } catch (e: Exception) {
            Result.Error("Data mapping TopRatedMovies error")
        }
    }

    @VisibleForTesting
    fun mapTopRatedMovies(responseTopRated: ResponseTopRated): List<Movie> =
        responseTopRated.results.map {
             Movie(it.title, it.overview, it.poster_path)
         }

}


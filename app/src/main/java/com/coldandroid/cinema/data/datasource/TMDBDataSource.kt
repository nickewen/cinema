package com.coldandroid.cinema.data.datasource

import com.coldandroid.cinema.BuildConfig
import com.coldandroid.cinema.data.entity.ResponseTopRated
import retrofit2.http.GET

interface TMDBDataSource {
    @GET("movie/top_rated?api_key="+BuildConfig.TMDB_TOKEN)
    suspend fun getTopRatedMovies(): ResponseTopRated
}
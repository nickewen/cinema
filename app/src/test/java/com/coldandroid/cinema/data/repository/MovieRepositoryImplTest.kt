package com.coldandroid.cinema.data.repository

import com.coldandroid.cinema.MainCoroutineRule
import com.coldandroid.cinema.data.datasource.TMDBDataSource
import com.coldandroid.cinema.data.entity.ResponseTopRated
import com.coldandroid.cinema.domain.repository.MovieRepository
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: MovieRepository
    @Mock private lateinit var mockDataSource: TMDBDataSource

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(Dispatchers.Main, mockDataSource)
    }

    @Test
    fun `when data is ok returns a Result Success`() {
        val movie1 = com.coldandroid.cinema.data.entity.Result(title= "title1", overview= "overview1", poster_path = "poster1")
        val movie2 = com.coldandroid.cinema.data.entity.Result(title= "title2", overview= "overview2", poster_path = "poster2")
        val response = ResponseTopRated(arrayListOf(movie1, movie2))
        mockDataSource.stub {
            on { runBlocking { getTopRatedMovies()} }.thenReturn(response)
        }
        runBlocking {
            val result = repository.getTopRatedMovies()

            assert(result is com.coldandroid.cinema.domain.entity.Result.Success)
            assert((result as com.coldandroid.cinema.domain.entity.Result.Success).data.size == 2)
        }
    }

    @Test
    fun `when there is an issue with data returns Result Error`() {
        mockDataSource.stub {
            on { runBlocking { getTopRatedMovies()} }.thenThrow(RuntimeException())
        }
        runBlocking {
            val result = repository.getTopRatedMovies()

            assert(result is com.coldandroid.cinema.domain.entity.Result.Error)
        }
    }

}
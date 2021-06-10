package com.coldandroid.cinema.domain.usecase.getmovies

import com.coldandroid.cinema.domain.entity.Movie
import com.coldandroid.cinema.domain.repository.MovieRepository
import com.coldandroid.cinema.domain.usecase.network.IsConnectedUseCase
import com.coldandroid.cinema.domain.entity.Result
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExploreMoviesUseCaseImplTest {
    private lateinit var useCase: ExploreMoviesUseCase
    @Mock private lateinit var mockIsConnectedUseCase: IsConnectedUseCase
    @Mock private lateinit var mockMovieRepository: MovieRepository

    @Before
    fun setUp() {
        useCase = ExploreMoviesUseCaseImpl(mockIsConnectedUseCase, mockMovieRepository)
    }

    @Test
    fun `return error when there is no internet connection`() {
        mockIsConnectedUseCase.stub { on {isConnected()}.doReturn(false) }

        runBlocking {
            val result = useCase.getMovies()
            assertEquals(result, Result.Error() )
        }
    }

    @Test
    fun `return success when there is internet connection and data are ok`() {
        val emptyList = emptyList<Movie>()
        mockIsConnectedUseCase.stub { on {isConnected()}.doReturn(true) }
        mockMovieRepository.stub { on { runBlocking { getTopRatedMovies()}} .doReturn(Result.Success(emptyList)) }

        runBlocking {
            val result = useCase.getMovies()
            assertEquals(result, Result.Success(emptyList) )
        }
    }

    @Test
    fun `return error when there is internet connection and data have issues`() {
        mockIsConnectedUseCase.stub { on {isConnected()}.doReturn(true) }
        mockMovieRepository.stub { on { runBlocking { getTopRatedMovies()}} .doReturn(Result.Error()) }

        runBlocking {
            val result = useCase.getMovies()
            assertEquals(result, Result.Error())
        }
    }
}
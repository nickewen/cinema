package com.coldandroid.cinema.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.coldandroid.cinema.MainCoroutineRule
import com.coldandroid.cinema.domain.entity.Movie
import com.coldandroid.cinema.domain.usecase.getmovies.ExploreMoviesUseCase
import com.coldandroid.cinema.domain.entity.Result
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel
    @Mock lateinit var mockUseCase: ExploreMoviesUseCase
    @Mock lateinit var viewStateObserver: Observer<ViewState>

    @Before
    fun setUp() {
        viewModel = HomeViewModel(mockUseCase)
    }

    @Test
    fun `getMovies with no results`() {
        val emptyList = emptyList<Movie>()
        val emptyResultList = emptyList<MovieUI>()

        mockUseCase.stub {
            on { runBlocking { getMovies() } } doReturn Result.Success(emptyList)
        }
        viewModel.getMovies()

        assertEquals(viewModel.viewState.value, ViewState.MoviesLoaded(emptyResultList))
    }

    @Test
    fun `getMovies with one result`() {
        val title = "title"
        val desc = "desc"
        val posterUrl = "posterlUrl"
        val movie = Movie(title, desc, posterUrl)
        val movieUI = MovieUI(title, desc, posterUrl)

        mockUseCase.stub {
            on { runBlocking { getMovies() } } doReturn Result.Success(listOf(movie))
        }
        viewModel.getMovies()

        assertEquals(viewModel.viewState.value, ViewState.MoviesLoaded(listOf(movieUI)))
    }

    @Test
    fun `getMovies with error`() {
        val errorMessage = "exception"
        mockUseCase.stub {
            on { runBlocking { getMovies() } } doReturn Result.Error(errorMessage)
        }
        viewModel.getMovies()

        assertEquals(viewModel.viewState.value, ViewState.MoviesLoadFailure(errorMessage))
    }

    @Test
    fun `getMovies triggers progress bar first`() {
        val errorMessage = "errorMessage"
        viewModel.viewState.observeForever(viewStateObserver)
        mockUseCase.stub {
            on { runBlocking { getMovies() } } doReturn Result.Error(errorMessage)
        }
        viewModel.getMovies()

        verify(viewStateObserver).onChanged(ViewState.Loading)
        verify(viewStateObserver).onChanged(ViewState.MoviesLoadFailure(errorMessage))
    }
}
package com.coldandroid.cinema.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldandroid.cinema.domain.usecase.getmovies.ExploreMoviesUseCase
import com.coldandroid.cinema.domain.entity.Result
import kotlinx.coroutines.launch

class HomeViewModel(private val exploreMovies : ExploreMoviesUseCase): ViewModel() {

    val viewState = MutableLiveData<ViewState>()

    fun getMovies() {
        viewModelScope.launch {
            viewState.value = ViewState.Loading
            when (val result = exploreMovies.getMovies()) {
                is Result.Success -> { viewState.value = ViewState.MoviesLoaded(result.data.map { mapMovieToMovieUI(it) })  }
                is Result.Error -> { viewState.value = ViewState.MoviesLoadFailure(result.error)}
            }
        }
    }
}

sealed class ViewState {
    object Loading: ViewState()
    data class MoviesLoaded(val movies: List<MovieUI>): ViewState()
    data class MoviesLoadFailure(val errorMessage: String?): ViewState()
}
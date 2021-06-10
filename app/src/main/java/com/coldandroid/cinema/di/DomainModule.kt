package com.coldandroid.cinema.di

import com.coldandroid.cinema.domain.usecase.getmovies.ExploreMoviesUseCase
import com.coldandroid.cinema.domain.usecase.getmovies.ExploreMoviesUseCaseImpl
import com.coldandroid.cinema.domain.usecase.network.IsConnectedUseCase
import com.coldandroid.cinema.domain.usecase.network.IsConnectedUseCaseImpl
import org.koin.dsl.module

val domainModule = module {

    factory<ExploreMoviesUseCase> {
        ExploreMoviesUseCaseImpl(get(), get())
    }
    factory<IsConnectedUseCase> {
        IsConnectedUseCaseImpl(get())
    }
}
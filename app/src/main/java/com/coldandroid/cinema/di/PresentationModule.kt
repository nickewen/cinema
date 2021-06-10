package com.coldandroid.cinema.di

import com.coldandroid.cinema.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { HomeViewModel(get()) }
}
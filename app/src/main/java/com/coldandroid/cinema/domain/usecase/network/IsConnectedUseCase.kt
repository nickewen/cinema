package com.coldandroid.cinema.domain.usecase.network

import com.coldandroid.cinema.domain.repository.NetworkRepository

interface IsConnectedUseCase {
    fun isConnected(): Boolean
}

class IsConnectedUseCaseImpl(private val networkRepository: NetworkRepository): IsConnectedUseCase {
    override fun isConnected(): Boolean = networkRepository.isNetworkConnected()
}
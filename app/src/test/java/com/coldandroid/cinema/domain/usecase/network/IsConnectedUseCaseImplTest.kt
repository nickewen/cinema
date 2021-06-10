package com.coldandroid.cinema.domain.usecase.network

import com.coldandroid.cinema.domain.repository.NetworkRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IsConnectedUseCaseImplTest {

    private lateinit var useCase: IsConnectedUseCase
    @Mock private lateinit var mockNetworkRepository: NetworkRepository

    @Before
    fun setUp() {
        useCase = IsConnectedUseCaseImpl(mockNetworkRepository)
    }

    @Test
    fun `return true when is connected`() {
        mockNetworkRepository.stub { on {isNetworkConnected()}.doReturn(true) }

        val isConnected = useCase.isConnected()

        assertTrue(isConnected)
    }

    @Test
    fun `return false when is not connected`() {
        mockNetworkRepository.stub { on {isNetworkConnected()}.doReturn(false) }

        val isConnected = useCase.isConnected()

        assertFalse(isConnected)
    }
}
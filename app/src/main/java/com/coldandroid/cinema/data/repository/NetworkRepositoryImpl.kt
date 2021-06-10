package com.coldandroid.cinema.data.repository

import android.content.Context
import android.net.ConnectivityManager
import com.coldandroid.cinema.domain.repository.NetworkRepository

class NetworkRepositoryImpl(private val context: Context): NetworkRepository {
    override fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}
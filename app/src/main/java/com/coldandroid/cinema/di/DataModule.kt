package com.coldandroid.cinema.di

import com.coldandroid.cinema.BuildConfig
import com.coldandroid.cinema.data.datasource.TMDBDataSource
import com.coldandroid.cinema.data.repository.NetworkRepositoryImpl
import com.coldandroid.cinema.data.repository.MovieRepositoryImpl
import com.coldandroid.cinema.domain.repository.MovieRepository
import com.coldandroid.cinema.domain.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    factory { HttpLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideTMDBApi(get()) }
    single { provideRetrofit(get()) }

    single<NetworkRepository> {
        NetworkRepositoryImpl(androidContext())
    }

    single<MovieRepository> {
        MovieRepositoryImpl(Dispatchers.IO, get())
    }
}

fun provideOkHttpClient(loggerInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val builder = OkHttpClient().newBuilder()
    if (BuildConfig.DEBUG) {
        loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggerInterceptor)
    }
    return builder.build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideTMDBApi(retrofit: Retrofit): TMDBDataSource = retrofit.create(
    TMDBDataSource::class.java
)

const val API_URL = "https://api.themoviedb.org/3/"

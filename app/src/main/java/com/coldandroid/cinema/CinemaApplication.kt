package com.coldandroid.cinema

import android.app.Application
import com.coldandroid.cinema.di.dataModule
import com.coldandroid.cinema.di.domainModule
import com.coldandroid.cinema.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CinemaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@CinemaApplication)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}
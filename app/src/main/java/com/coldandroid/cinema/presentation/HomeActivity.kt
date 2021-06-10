package com.coldandroid.cinema.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coldandroid.cinema.R
import com.coldandroid.cinema.presentation.home.HomeFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame) ?:
        supportFragmentManager.beginTransaction().replace(R.id.contentFrame, HomeFragment()).commit()
    }
}
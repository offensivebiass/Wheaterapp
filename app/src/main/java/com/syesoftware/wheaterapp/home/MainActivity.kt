package com.syesoftware.wheaterapp.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.syesoftware.wheaterapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }
}

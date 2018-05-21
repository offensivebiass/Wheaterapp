package com.syesoftware.wheaterapp.api

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.syesoftware.wheaterapp.utils.MyApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Alex Frías on 17/05/2018.
 */
object RestClient {

    private val ROOT_URL = "http://api.openweathermap.org/data/"


    /**
     * Get Retrofit Instance
     */
    fun getRetrofitInstance(): Retrofit {

        return Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getService() : ApiService {
        return getRetrofitInstance().create<ApiService>(ApiService::class.java)
    }

}
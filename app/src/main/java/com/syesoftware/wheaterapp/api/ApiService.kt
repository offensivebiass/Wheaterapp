package com.syesoftware.wheaterapp.api

import com.syesoftware.wheaterapp.model.Weather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("x-api-key: fbba3cce2c745b1f963df4cbea208546")
    @GET("2.5/weather?")
    fun getWeatherFromLocation(@Query("lat") latitude : String,
                               @Query("lon") longitude: String,
                               @Query("units") unit : String): Observable<Weather>
}
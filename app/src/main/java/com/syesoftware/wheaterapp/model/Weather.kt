package com.syesoftware.wheaterapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
        val coord: Coord,
        var weather: List<WeatherList>,
        var base: String,
        var main: Main,
        var visibility: Int,
        var wind: Wind,
        var clouds: Clouds,
        var dt: Int,
        var sys: Sys,
        var id: Int,
        var name: String,
        var cod: Int
)

data class Coord(val lon: Double, val lat: Double)

data class WeatherList(val id: Int, val main: String, val description: String, val icon: String)

data class Main(
        val temp: Int,
        val pressure: Int,
        val humidity: Int,
        val tempMin: Int,
        val tempMax: Int
)

data class Wind(val speed: Double, val deg: Int)

data class Clouds (val all: Int)

data class Sys (val type: Int,
                val id: Int,
                val message: Double,
                val country: String,
                val sunrise: Int,
                val sunset: Int)

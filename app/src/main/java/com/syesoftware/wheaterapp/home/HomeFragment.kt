package com.syesoftware.wheaterapp.home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syesoftware.wheaterapp.R
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.LocationManager
import android.text.Html
import com.syesoftware.wheaterapp.api.RestClient
import com.syesoftware.wheaterapp.model.Weather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.DateFormat
import java.util.*
import android.app.ProgressDialog
import android.util.Log
import androidx.navigation.fragment.NavHostFragment

class HomeFragment : Fragment() {

    private lateinit var progress: ProgressDialog
    private lateinit var locationLongitude: String
    private lateinit var locationLatitude: String
    private lateinit var weatherFont: Typeface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = ProgressDialog(context)

        changeCityButton.setOnClickListener({
            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_changeCityFragment)
        })


        weatherFont = Typeface.createFromAsset(context?.assets, "fonts/weathericons_regular_webfont.ttf")

        getLocation()
        getData()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this.requireContext(), ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(context!! as Activity,
                    arrayOf(ACCESS_FINE_LOCATION), targetRequestCode)
        } else {
            val lm = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            locationLongitude = location.longitude.toString()
            locationLatitude = location.latitude.toString()
        }
    }

    private fun getData() {
        Log.d("location Lat and Long", locationLatitude + " " + locationLongitude)
        progress.show()
        val service = RestClient.getService()
        val call = service.getWeatherFromLocation(locationLatitude, locationLongitude, "metric")
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    reloadData(result)
                }, { error ->
                    error.printStackTrace()
                    Log.e("call error", error.toString())
                    progress.dismiss()
                })

    }

    private fun reloadData(weather: Weather?) {
        val df = DateFormat.getDateTimeInstance()

        if (weather != null) {
            cityTextView.text = weather.name
            updatedTextView.text = df.format(Date((weather.dt * 1000).toLong()))
            val icon = setWeatherIcon(weather.weather[0].id,
                    (weather.sys.sunrise * 1000).toLong(),
                    (weather.sys.sunset * 1000).toLong())
            weatherIconTextView.typeface = weatherFont
            weatherIconTextView.text = Html.fromHtml(icon)
            currentTemperatureTextView.text = weather.main.temp.toString() + "°C"

        }
        progress.dismiss()
    }

    fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long): String {
        val id = actualId / 100
        var icon = ""
        if (actualId == 800) {
            val currentTime = Date().time
            icon = if (currentTime in sunrise..(sunset - 1)) {
                "&#xf00d;"
            } else {
                "&#xf02e;"
            }
        } else {
            when (id) {
                2 -> icon = "&#xf01e;"
                3 -> icon = "&#xf01c;"
                7 -> icon = "&#xf014;"
                8 -> icon = "&#xf013;"
                6 -> icon = "&#xf01b;"
                5 -> icon = "&#xf019;"
            }
        }
        return icon
    }
}

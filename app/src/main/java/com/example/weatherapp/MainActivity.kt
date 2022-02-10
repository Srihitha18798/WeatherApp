package com.example.weatherapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var mLocationManager: LocationManager
    lateinit var mLocationListener: LocationListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        cityFinder.setOnClickListener {

            val intent = Intent(this, CityFinder::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val mIntent = intent
        val city = mIntent.getStringExtra("City")
        if (city != null) {
            getWeatherForNewCity(city)
        } else {
            getWeatherForCurrentLocation()
        }
    }

    private fun getWeatherForNewCity(city: String) {
        val params = RequestParams()
        params.put("q", city)
        params.put("appid", app_id)
        letsDoSomeNetworking(params)
    }


    private fun getWeatherForCurrentLocation() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mLocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude.toString()
                val logitude = location.longitude.toString()
                val params: RequestParams = RequestParams();
                params.put("lat", latitude)
                params.put("lon", logitude)
                params.put("appid", app_id)
                letsDoSomeNetworking(params)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderDisabled(provider: String) {

            }

            override fun onProviderEnabled(provider: String) {

            }


        }

        //mLocationManager.requestLocationUpdates(location_provider,min_time,min_distance,mLocationListener)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), request_code
            )
            return


        }
        mLocationManager.requestLocationUpdates(
            location_provider,
            min_time.toLong(),
            min_distance.toFloat(),
            mLocationListener
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == request_code) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Locationget Successfully", Toast.LENGTH_SHORT).show()
                getWeatherForCurrentLocation()
            } else {
                //user denied the permission
            }
        }
    }

    fun letsDoSomeNetworking(params: RequestParams) {
        val client: AsyncHttpClient = AsyncHttpClient()
        client.get(weather_url, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject
            ) {

                //Toast.makeText(this@MainActivity, "Data Get Success", Toast.LENGTH_SHORT).show()
                val weatherD: WeatherDetails? = WeatherDetails.fromJson(response)
                if (weatherD != null) {
                    updateUI(weatherD)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
            }

        })
    }

    fun updateUI(weather: WeatherDetails) {
        temperature.text = weather.getmTemperature()
        cityName.text = weather.getmCity()
        weatherCondition.text = weather.mweatherType
        val resorceId = resources.getIdentifier(weather.getmIcon(), "drawable", packageName)
        weatherIcon.setImageResource(resorceId)
    }


}
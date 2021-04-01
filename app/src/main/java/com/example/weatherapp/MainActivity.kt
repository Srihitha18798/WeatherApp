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
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val app_id="28e1a13aa92ec8485c26eab38b82b23f"
    val weather_url="https://api.openweathermap.org/data/2.5/weather"
    val min_time=5000
    val min_distance=1000
    val request_code=101
    val location_provider=LocationManager.GPS_PROVIDER


    lateinit var weatherState:TextView
    lateinit var temperature:TextView
    lateinit var weatherIcon:ImageView
    lateinit var mCityFinder:RelativeLayout
    lateinit var nameOfCity:TextView
    lateinit var mLocationManager:LocationManager
    lateinit var mLocationListener:LocationListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherState= findViewById<TextView>(R.id.weatherCondition)
        temperature=findViewById<TextView>(R.id.temperature)
        weatherIcon=findViewById<ImageView>(R.id.weatherIcon)
        mCityFinder=findViewById<RelativeLayout>(R.id.cityFinder)
        nameOfCity=findViewById<TextView>(R.id.cityName)

       mCityFinder.setOnClickListener {

           var intent=Intent(this,CityFinder::class.java)
           startActivity(intent)
       }
    }

    override fun onResume() {
        super.onResume()
        getWeatherForCurrentLocation()

    }

    private fun getWeatherForCurrentLocation(){
        mLocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mLocationListener = object :LocationListener{
            override fun onLocationChanged(location: Location) {
                var latitude=location.latitude.toString()
                var logitude=location.longitude.toString()






                var params: RequestParams =RequestParams();
                params.put("lat", latitude)
                params.put("lon",logitude)
                params.put("appid",app_id)
                letsDoSomeNetworking(params)


            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                //super.onStatusChanged(provider, status, extras)
            }

            override fun onProviderDisabled(provider: String) {
                //
            }

            override fun onProviderEnabled(provider: String) {
                //
            }


        }

        //mLocationManager.requestLocationUpdates(location_provider,min_time,min_distance,mLocationListener)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),request_code)
            return


        }
        mLocationManager.requestLocationUpdates(
            location_provider,
            min_time.toLong(),
            min_distance.toFloat(),
            mLocationListener
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==request_code){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Locationget Successfully",Toast.LENGTH_SHORT).show()
                getWeatherForCurrentLocation()
            }
            else{
                //user denied the permission
            }
        }
    }
    fun letsDoSomeNetworking(params: RequestParams){
        var client:AsyncHttpClient= AsyncHttpClient()
        client.get(weather_url,params,object :JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int,headers: Array<out Header>?,response: JSONObject) {

                Toast.makeText(this@MainActivity,"Data Get Success",Toast.LENGTH_SHORT).show()
                var weatherD: WeatherDetails? = WeatherDetails.fromJson(response)
                if (weatherD != null) {
                    updateUI(weatherD)
                }
                          //super.onSuccess(statusCode, headers, response)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                //super.onFailure(statusCode, headers, responseString, throwable)
            }

        })
    }

    fun updateUI(weather:WeatherDetails){
        temperature.setText(weather.getmTemperature())
        nameOfCity.setText(weather.getmCity())
        weatherState.setText(weather.mweatherType)
        var resorceId=resources.getIdentifier(weather.getmIcon(),"drawable",packageName)
        weatherIcon.setImageResource(resorceId)
    }

    override fun onPause() {
        super.onPause()
        if(mLocationManager!=null){
            mLocationManager.removeUpdates(mLocationListener)
        }
    }

}
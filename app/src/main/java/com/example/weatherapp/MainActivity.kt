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
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {
    /*val app_id="28e1a13aa92ec8485c26eab38b82b23f"
    val weather_url="https://home.openweathermap.org/data/2.5/weather"
    val min_time=5000
    val min_distance=1000
    val request_code=101

    var location_provider=LocationManager.GPS_PROVIDER*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*var weatherState=findViewById<TextView>(R.id.weatherCondition)
        var temperature=findViewById<TextView>(R.id.temperature)
        var weatherIcon=findViewById<ImageView>(R.id.weatherIcon)
        var mCityFinder=findViewById<RelativeLayout>(R.id.cityFinder)
        var nameOfCity=findViewById<TextView>(R.id.cityName)

       mCityFinder.setOnClickListener {
           var intent=Intent(this,CityFinder::class.java)
           startActivity(intent)
       }
    }

    override fun onResume() {
        super.onResume()
        getWeatherForCurrentLocation()

    }

    fun getWeatherForCurrentLocation(){
        var mLocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val mLocationListener = object :LocationListener{
            override fun onLocationChanged(location: Location) {
                var latitude=location.latitude
                var logitude=location.longitude
            }


        }

        //mLocationManager.requestLocationUpdates(location_provider,min_time,min_distance,mLocationListener)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
        if(requestCode==request_code){
            if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Locationget Successfully",Toast.LENGTH_SHORT).show()
                getWeatherForCurrentLocation()
            }
            else{
                //
            }
        }*/
    }


}
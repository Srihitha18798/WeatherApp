package com.example.weatherapp

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class WeatherDetails{

    lateinit var mTemperature: String
    lateinit var micon: String
    lateinit var mCity: String
    lateinit var mweatherType: String
    var mCondition=0

companion object {
    fun fromJson(jsonObject: JSONObject): WeatherDetails? {
        try {
            val weatherD = WeatherDetails()
            weatherD.mCity = jsonObject.getString("name")
            weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id")
            weatherD.mweatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main")
            weatherD.micon = WeatherDetails.updateWeatherIcon(weatherD.mCondition)
            var tempResult = jsonObject.getJSONObject("main").getDouble("temp")-273.15
            var roundedValue=Math.rint(tempResult).toInt()
            weatherD.mTemperature=roundedValue.toString()
            return weatherD

        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }

    }

    private fun updateWeatherIcon(condition: Int): String {
        if (condition >= 0 && condition <= 300) {
            return "images"
        } else if (condition >= 300 && condition <= 500) {
            return "images"
        } else if (condition >= 500 && condition <= 600) {
            return "images"
        } else if (condition >= 600 && condition <= 700) {
            return "images"
        } else if (condition >= 701 && condition <= 771) {
            return "images"
        } else if (condition >= 772 && condition < 800) {
            return "images"
        } else if (condition == 800) {
            return "images"
        } else if (condition >= 801 && condition <= 804) {
            return "images"
        } else if (condition >= 900 && condition <= 902) {
            return "images"
        }
        if (condition == 903) {
            return "images"
        }
        if (condition == 904) {
            return "images"
        }
        if (condition >= 905 && condition <= 1000) {
            return "images"
        }
        else return "images"
    }


}
    fun getmTemperature(): String {
        return "$mTemperature°C"
    }

    fun getmCity(): String {
        return mCity
    }

    fun getmIcon():String{
        return micon
    }
    fun getmWeatherType():String{
        return mweatherType
    }
}
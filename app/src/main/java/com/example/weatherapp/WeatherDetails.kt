package com.example.weatherapp

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class WeatherDetails {

    lateinit var mTemperature: String
    lateinit var micon: String
    lateinit var mCity: String
    lateinit var mweatherType: String
    var mCondition = 0

    companion object {
        fun fromJson(jsonObject: JSONObject): WeatherDetails? {
            try {
                val weatherD = WeatherDetails()
                weatherD.mCity = jsonObject.getString("name")
                weatherD.mCondition =
                    jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id")
                weatherD.mweatherType =
                    jsonObject.getJSONArray("weather").getJSONObject(0).getString("main")
                weatherD.micon = WeatherDetails.updateWeatherIcon(weatherD.mCondition)
                val tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15
                val roundedValue = Math.rint(tempResult).toInt()
                weatherD.mTemperature = roundedValue.toString()
                return weatherD

            } catch (e: JSONException) {
                e.printStackTrace()
                return null
            }

        }

        private fun updateWeatherIcon(condition: Int): String {
            if (condition >= 0 && condition <= 300) {
                return "thunder"
            } else if (condition >= 300 && condition <= 500) {
                return "rain"
            } else if (condition >= 500 && condition <= 600) {
                return "rain"
            } else if (condition >= 600 && condition <= 700) {
                return "snow"
            } else if (condition >= 701 && condition <= 771) {
                return "h"
            } else if (condition >= 772 && condition < 800) {
                return "h"
            } else if (condition == 800) {
                return "clear"
            } else if (condition >= 801 && condition <= 804) {
                return "clouds"
            } else if (condition >= 900 && condition <= 902) {
                return "clouds"
            }
            if (condition == 903) {
                return "more_clouds"
            }
            if (condition == 904) {
                return "more_clouds"
            }
            if (condition >= 905 && condition <= 1000) {
                return "more_clouds"
            } else return "download"
        }


    }

    fun getmTemperature(): String {
        return "$mTemperature°C"
    }

    fun getmCity(): String {
        return mCity
    }

    fun getmIcon(): String {
        return micon
    }

    fun getmWeatherType(): String {
        return mweatherType
    }
}
package com.example.tfgagua.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current
)

data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("localtime") val localtime: String
)

data class Current(
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("precip_mm") val precipMm: Double

)

data class Condition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("code") val code: Int
)

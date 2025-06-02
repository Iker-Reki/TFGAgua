package com.example.tfgagua.conexion

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://app-3ce35011-caef-4493-b85d-79450b2ea265.cleverapps.io"

    private const val BASE_URL_WEATHER_API = "http://api.weatherapi.com/"

    val instancia: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val retrofitWeatherApi: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER_API) // Use HTTPS for WeatherAPI
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApiService: ApiService by lazy {
        retrofitWeatherApi.create(ApiService::class.java)
    }


}
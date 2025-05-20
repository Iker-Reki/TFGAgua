package com.example.tfgagua.conexion

import com.example.tfgagua.data.LoginRequest
import com.example.tfgagua.data.LoginResponse
import com.example.tfgagua.data.Usu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/usuario")
    fun obtenerUsu(): Call<List<Usu>>

    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
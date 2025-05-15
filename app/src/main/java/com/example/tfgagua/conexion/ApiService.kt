package com.example.tfgagua.conexion

import com.example.tfgagua.data.Usu
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/usuario")
    fun obtenerUsu(): Call<List<Usu>>

    @GET("api/comprobarCredenciales")
    fun comprobarCredenciales ()
}
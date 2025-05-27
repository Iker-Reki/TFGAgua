package com.example.tfgagua.conexion

import com.example.tfgagua.data.CambioContrasena
import com.example.tfgagua.data.CantCorreo
import com.example.tfgagua.data.Confederacion
import com.example.tfgagua.data.LoginRequest
import com.example.tfgagua.data.LoginResponse
import com.example.tfgagua.data.RegistroResponse
import com.example.tfgagua.data.Usu
import com.example.tfgagua.data.UsuRegistro
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("api/usuario")
    fun obtenerUsu(
    ): Call<List<Usu>>

    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("api/check-correo")
    fun existCorreo(
        @Query("correo") email: String
    ): Response<CantCorreo>

    @POST("api/registro")
    suspend fun registrarUsu(
        @Body usuario: UsuRegistro
    ): Response<RegistroResponse>

    @POST("api/cambio-contra")
    suspend fun cambiarContrasena(
        @Body request: CambioContrasena
    ): Response<RegistroResponse>
    // Confederaciones
    @GET("api/confederaciones")
    suspend fun obtenerConfederaciones(): Response<List<Confederacion>>
}
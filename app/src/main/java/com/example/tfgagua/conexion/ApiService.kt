package com.example.tfgagua.conexion

import com.example.tfgagua.data.CambioContrasena
import com.example.tfgagua.data.CantCorreo
import com.example.tfgagua.data.Confederacion
import com.example.tfgagua.data.ConfederacionDetalle
import com.example.tfgagua.data.DatoAgua
import com.example.tfgagua.data.FavoritoRequest
import com.example.tfgagua.data.LoginRequest
import com.example.tfgagua.data.LoginResponse
import com.example.tfgagua.data.RegistroResponse
import com.example.tfgagua.data.Usu
import com.example.tfgagua.data.UsuRegistro
import com.example.tfgagua.data.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.DELETE
import retrofit2.http.Path

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

    //Obtener confederaciones favoritas para un usuario
    @GET("api/confederaciones/favoritas")
    suspend fun obtenerConfederacionesFavoritas(@Query("idUsu") idUsu: Int): Response<List<Confederacion>>

    // Añadir una confederación a favoritos
    @POST("api/confederaciones/favoritas")
    suspend fun añadirFavorito(@Body favoritoRequest: FavoritoRequest): Response<RegistroResponse>

    // Eliminar una confederación de favoritos
    @DELETE("api/confederaciones/favoritas")
    suspend fun eliminarFavorito(@Query("idUsu") idUsu: Int, @Query("idConfe") idConfe: Int): Response<RegistroResponse>

    // Obtener detalles de una confederación por ID
    @GET("api/confederacion/{idConfe}")
    suspend fun obtenerDetalleConfederacion(@Path("idConfe") idConfe: Int): Response<ConfederacionDetalle>

    // Obtener datos de nivel de agua para una confederación por fecha
    @GET("api/datos_confederacion/{confeId}")
    suspend fun obtenerDatosAgua(@Path("confeId") confeId: Int, @Query("date") date: String): Response<List<DatoAgua>>

    // WeatherAPI
    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("lang") language: String = "es" // Added language parameter with default 'es'
    ): Response<WeatherResponse>
}
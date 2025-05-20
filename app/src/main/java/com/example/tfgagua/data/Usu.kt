package com.example.tfgagua.data

import com.google.gson.annotations.SerializedName


data class Usu(
    @SerializedName("idUsu") val id: Int,
    @SerializedName("nombreUsu") val nombre: String,
    @SerializedName("ape1Usu") val apellido1: String,
    @SerializedName("ape2Usu") val apellido2: String,
    @SerializedName("correoUsu") val correo: String,
    @SerializedName("contraUsu") val contrasena:String




    //Embalses asociados
   // val embalses:List<Embalse>
)

/**
 * El objeto que va a mandar al backend
 */
data class LoginRequest(
    val correo: String,
    val contrasena: String
)

/**
 * El pbjeto que va a recibir del backend
 */
data class UsuarioResponse(
    val id: Int,
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val correo: String
)

/**
 * Respuesta de la API para el login
 */
data class LoginResponse(
    val success: Boolean,
    val usuario: UsuarioResponse?,
    val error: String?
)

/**
 * Cantidad de correos que coinciden
 */
data class CantCorreo(
    @SerializedName("count") val count: Int
)

/**
 * Datos del usuario que vamos a insertar
 */
data class UsuRegistro(
    @SerializedName("nombreUsu") val nombre: String,
    @SerializedName("ape1Usu") val apellido1: String,
    @SerializedName("ape2Usu") val apellido2: String,
    @SerializedName("correoUsu") val correo: String,
    @SerializedName("contraUsu") val contrasena: String
)

/**
 * Respuesta de la API para el registro
 */
data class RegistroResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String?
)


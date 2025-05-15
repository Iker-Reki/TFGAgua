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

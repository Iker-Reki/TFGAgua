package com.example.tfgagua.data


import com.google.gson.annotations.SerializedName

data class Confederacion(
    @SerializedName("idConfe") val id: Int,
    @SerializedName("nombreConfe") val nombre: String,
    @SerializedName("ubicacionConfe") val ubicacion: String
)

data class ConfederacionDetalle(
    @SerializedName("idConfe") val id: Int,
    @SerializedName("nombreConfe") val nombre: String,
    @SerializedName("ubicacionConfe") val ubicacion: String,
    @SerializedName("capacidadConfe") val capacidad: Int,
    @SerializedName("fecConstConfe") val fecConstruccion: String, // Or use Date type
    @SerializedName("alturaConfe") val altura: Int
)

data class DatoAgua(
    @SerializedName("nivelDato") val nivel: Int,
    @SerializedName("fecDato") val fecha: String,
    @SerializedName("hora_dato") val hora: String
)
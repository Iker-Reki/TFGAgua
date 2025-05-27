package com.example.tfgagua.data


import com.google.gson.annotations.SerializedName

data class Confederacion(
    @SerializedName("idConfe") val id: Int,
    @SerializedName("nombreConfe") val nombre: String,
    @SerializedName("ubicacionConfe") val ubicacion: String
    // Puedes añadir más campos si los necesitas para DetallesVista.kt luego,
    // como capacidadConfe, fecConstConfe, alturaConfe del SQL.
)
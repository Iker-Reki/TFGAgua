package com.example.tfgagua.model

data class Usu(
    val usuApe1:String,
    val usuApe2:String,
    val usuContra:String,
    val usuCorreo: String,
    val usuName:String,

    //Embalses asociados
    val embalses:List<Embalse>
)

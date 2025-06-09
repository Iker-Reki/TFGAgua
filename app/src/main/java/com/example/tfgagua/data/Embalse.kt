package com.example.tfgagua.data

import java.util.Date

data class Embalse(
    val embalseName: String,
    val embalseCant: Int,
    val embalseFecConst: Date,

    //Lista de datos de los sensores
    //val datos: List<Dato>
)

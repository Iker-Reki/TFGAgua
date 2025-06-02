package com.example.tfgagua.data

import com.google.gson.annotations.SerializedName

data class FavoritoRequest(
    @SerializedName("idUsu") val idUsu: Int,
    @SerializedName("idConfe") val idConfe: Int
)

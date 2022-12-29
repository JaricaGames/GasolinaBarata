package com.jarica.preciogasolina.data.network.response

import com.google.gson.annotations.SerializedName

data class Gasolina(
    @SerializedName("IDProducto") var iDProducto: String,
    @SerializedName("NombreProducto") var nombreProducto: String,
    @SerializedName("NombreProductoAbreviatura") var nombreProductoAbreviatura: String,
)

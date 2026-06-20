package com.jarica.preciogasolina.data.network.Retrofit.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Gasolina(
    @SerializedName("IDProducto") var iDProducto: String,
    @SerializedName("NombreProducto") var nombreProducto: String,
    @SerializedName("NombreProductoAbreviatura") var nombreProductoAbreviatura: String,
)

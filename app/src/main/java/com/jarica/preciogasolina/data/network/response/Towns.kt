package com.jarica.preciogasolina.data.network.response

import com.google.gson.annotations.SerializedName

data class Towns(
    @SerializedName("CCAA") val CCAA: String,
    @SerializedName("IDCCAA") val IDCCAA: String,
    @SerializedName("IDMunicipio") val IDMunicipio: String,
    @SerializedName("IDProvincia") val IDProvincia: String,
    @SerializedName("Municipio") val Municipio: String,
    @SerializedName("Provincia") val Provincia: String
)
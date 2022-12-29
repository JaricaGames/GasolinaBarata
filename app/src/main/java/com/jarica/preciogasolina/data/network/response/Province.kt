package com.jarica.preciogasolina.data.network.response

import com.google.gson.annotations.SerializedName

data class Province(
    @SerializedName("IDPovincia") var IDProvincia: String,
    @SerializedName("IDCCAA") var IDCCA: String,
    @SerializedName("Provincia") var Provincia: String,
    @SerializedName("CCAA") var CCAA: String,


    )

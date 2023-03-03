package com.jarica.preciogasolina.data.network.Retrofit.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Province(
    @SerializedName("IDPovincia") var IDProvincia: String,
    @SerializedName("IDCCAA") var IDCCA: String,
    @SerializedName("Provincia") var Provincia: String,
    @SerializedName("CCAA") var CCAA: String,


    )

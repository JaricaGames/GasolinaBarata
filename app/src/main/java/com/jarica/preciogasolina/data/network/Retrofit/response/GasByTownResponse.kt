package com.jarica.preciogasolina.data.network.Retrofit.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GasByTownResponse(

    @SerializedName("Fecha") var fecha: String,
    @SerializedName("ListaEESSPrecio") var ListaEESSPrecio: ArrayList<GasolineraPorMunicipio>,
    @SerializedName("Nota") var nota: String,
    @SerializedName("ResultadoConsulta") var resultadoConsulta: String,

    )
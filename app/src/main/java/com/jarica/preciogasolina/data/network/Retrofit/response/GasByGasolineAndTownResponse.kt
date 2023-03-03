package com.jarica.preciogasolina.data.network.Retrofit.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GasByGasolineAndTownResponse(
    @SerializedName("Fecha") var fecha: String,
    @SerializedName("ListaEESSPrecio") var ListaEESSPrecio: ArrayList<GasolineraPorGasolinaYMunicipio>,
    @SerializedName("Nota") var nota: String,
    @SerializedName("ResultadoConsulta") var resultadoConsulta: String,
)

package com.jarica.preciogasolina.data.network.response

import com.google.gson.annotations.SerializedName

data class GasByTownResponse(

    @SerializedName("Fecha") var fecha: String,
    @SerializedName("ListaEESSPrecio") var ListaEESSPrecio: ArrayList<GasolineraPorMunicipio>,
    @SerializedName("Nota") var nota: String,
    @SerializedName("ResultadoConsulta") var resultadoConsulta: String,

    )
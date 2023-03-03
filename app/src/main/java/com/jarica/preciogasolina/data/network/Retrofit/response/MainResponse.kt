package com.jarica.preciogasolina.data.network.Retrofit.response

import androidx.annotation.Keep

@Keep
data class MainResponse(
    val Fecha: String,
    val ListaEESSPrecio: List<GasolineraPorMunicipio>,
    val Nota: String,
    val ResultadoConsulta: String
)
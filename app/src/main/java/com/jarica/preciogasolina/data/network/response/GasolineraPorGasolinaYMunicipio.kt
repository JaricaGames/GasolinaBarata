package com.jarica.preciogasolina.data.network.response

import com.google.gson.annotations.SerializedName

data class GasolineraPorGasolinaYMunicipio (
    @SerializedName("C.P.") var CP: String,
    @SerializedName("Dirección") var direccion: String,
    @SerializedName("Horario") var horario: String,
    @SerializedName("Latitud") var latitud: String,
    @SerializedName("Localidad") var localidad: String,
    @SerializedName("Longitud (WGS84)") var longitud: String,
    @SerializedName("Margen") var margen: String,
    @SerializedName("Municipio") var municipio: String,
    @SerializedName("PrecioProducto") var precioProducto: String,
    @SerializedName("Provincia") var provincia: String,
    @SerializedName("Remisión") var remision: String,
    @SerializedName("Rótulo") var rotulo: String,
    @SerializedName("Tipo Venta") var tipoVenta: String,
    @SerializedName("BioEtanol") var BioEtanol: String,
    @SerializedName("Éster metílico") var Estermetilico: String,
    @SerializedName("IDEESS") var iDEESS: String,
    @SerializedName("IDMunicipio") var iDMunicipio: String,
    @SerializedName("IDProvincia") var iDProvincia: String,
    @SerializedName("IDCCAA") var iDCCAA: String,
)
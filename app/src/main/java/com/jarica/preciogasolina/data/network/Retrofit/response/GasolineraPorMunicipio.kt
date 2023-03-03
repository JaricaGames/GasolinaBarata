package com.jarica.preciogasolina.data.network.Retrofit.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GasolineraPorMunicipio(
    @SerializedName("C.P.") var CP: String,
    @SerializedName("Dirección") var direccion: String,
    @SerializedName("Horario") var horario: String,
    @SerializedName("Latitud") var latitud: String,
    @SerializedName("Localidad") var localidad: String,
    @SerializedName("Longitud (WGS84)") var longitud: String,
    @SerializedName("Margen") var margen: String,
    @SerializedName("Municipio") var municipio: String,
    @SerializedName("Precio Biodiesel") var precioBiodiesel: String,
    @SerializedName("Precio Bioetanol") var precioBioetanol: String,
    @SerializedName("Precio Gas Natural Comprimido") var precioGasNaturalComprimido: String,
    @SerializedName("Precio Gas Natural Licuado") var precioGasNaturalLicuado: String,
    @SerializedName("Precio Gases licuados del petróleo") var precioGaseslicuadosdelpetroleo: String,
    @SerializedName("Precio Gasoleo A") var precioGasoleoA: String,
    @SerializedName("Precio Gasoleo B") var precioGasoleoB: String,
    @SerializedName("Precio Gasoleo Premium") var precioGasoleoPremium: String,
    @SerializedName("Precio Gasolina 95 E10") var precioGasolina95E10: String,
    @SerializedName("Precio Gasolina 95 E5") var precioGasolina95E5: String,
    @SerializedName("Precio Gasolina 95 E5 Premium") var precioGasolina95E5Premium: String,
    @SerializedName("Precio Gasolina 98 E10") var precioGasolina98E10: String,
    @SerializedName("Precio Gasolina 98 E5") var precioGasolina98E5: String,
    @SerializedName("Precio Hidrogeno") var precioHidrogeno: String,
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

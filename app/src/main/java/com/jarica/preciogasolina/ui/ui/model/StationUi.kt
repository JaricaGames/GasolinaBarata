package com.jarica.preciogasolina.ui.ui.model

import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import java.util.Locale

//MODELO UNIFICADO DE ESTACION PARA LA UI: SIRVE TANTO PARA LA BUSQUEDA POR MUNICIPIO
//(TODOS LOS CARBURANTES) COMO PARA LA BUSQUEDA POR CARBURANTE Y MUNICIPIO
data class FuelPrice(val nombre: String, val precio: Double)

data class StationUi(
    val id: String,
    val rotulo: String,
    val direccion: String,
    val localidad: String,
    val municipio: String,
    val provincia: String,
    val horario: String,
    val latitud: Double?,
    val longitud: Double?,
    val carburante: String,
    val precio: Double?,
    val precios: List<FuelPrice>
)

private val localeEs = Locale.forLanguageTag("es-ES")

fun parsePrecio(value: String?): Double? =
    value?.replace(',', '.')?.toDoubleOrNull()

fun parseCoordenada(value: String?): Double? =
    value?.replace(',', '.')?.toDoubleOrNull()

//FORMATO es-ES CON COMA Y 3 DECIMALES: 1.449 -> "1,449"
fun formatPrecio(value: Double): String =
    String.format(localeEs, "%.3f", value)

//DIFERENCIA FRENTE A LA MAS BARATA: "+0,03"
fun formatDelta(value: Double): String =
    String.format(localeEs, "+%.2f", value)

fun GasolineraPorMunicipio.preciosDisponibles(): List<FuelPrice> = listOfNotNull(
    parsePrecio(precioGasoleoA)?.let { FuelPrice("Gasóleo A", it) },
    parsePrecio(precioGasolina95E5)?.let { FuelPrice("Gasolina 95 E5", it) },
    parsePrecio(precioGasoleoPremium)?.let { FuelPrice("Gasóleo Premium", it) },
    parsePrecio(precioGasolina98E5)?.let { FuelPrice("Gasolina 98 E5", it) },
    parsePrecio(precioGasolina95E10)?.let { FuelPrice("Gasolina 95 E10", it) },
    parsePrecio(precioGasolina98E10)?.let { FuelPrice("Gasolina 98 E10", it) },
    parsePrecio(precioGasolina95E5Premium)?.let { FuelPrice("Gasolina 95 E5 Premium", it) },
    parsePrecio(precioGasoleoB)?.let { FuelPrice("Gasóleo B", it) },
    parsePrecio(precioBiodiesel)?.let { FuelPrice("Biodiésel", it) },
    parsePrecio(precioBioetanol)?.let { FuelPrice("Bioetanol", it) },
    parsePrecio(precioGasNaturalComprimido)?.let { FuelPrice("GNC", it) },
    parsePrecio(precioGasNaturalLicuado)?.let { FuelPrice("GNL", it) },
    parsePrecio(precioGaseslicuadosdelpetroleo)?.let { FuelPrice("GLP", it) },
    parsePrecio(precioHidrogeno)?.let { FuelPrice("Hidrógeno", it) }
)

fun GasolineraPorMunicipio.toStationUi(): StationUi {
    val precios = preciosDisponibles()
    val principal = precios.firstOrNull()
    return StationUi(
        id = iDEESS,
        rotulo = rotulo,
        direccion = direccion,
        localidad = localidad,
        municipio = municipio,
        provincia = provincia,
        horario = horario,
        latitud = parseCoordenada(latitud),
        longitud = parseCoordenada(longitud),
        carburante = principal?.nombre ?: "",
        precio = principal?.precio,
        precios = precios
    )
}

fun GasolineraPorGasolinaYMunicipio.toStationUi(carburante: String): StationUi {
    val precio = parsePrecio(precioProducto)
    return StationUi(
        id = iDEESS,
        rotulo = rotulo,
        direccion = direccion,
        localidad = localidad,
        municipio = municipio,
        provincia = provincia,
        horario = horario,
        latitud = parseCoordenada(latitud),
        longitud = parseCoordenada(longitud),
        carburante = carburante,
        precio = precio,
        precios = precio?.let { listOf(FuelPrice(carburante, it)) } ?: emptyList()
    )
}

package com.jarica.preciogasolina.ui.ui.Map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.*
import com.jarica.preciogasolina.ui.ui.Components.AdBanner
import com.jarica.preciogasolina.ui.ui.Components.StationCard
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.model.StationUi
import com.jarica.preciogasolina.ui.ui.model.formatPrecio
import com.jarica.preciogasolina.ui.ui.model.toStationUi
import androidx.compose.runtime.LaunchedEffect

const val PADDING_MAP = 100

@Composable
fun MapUi(mapViewModel: MapViewModel, listViewModel: ListViewModel) {

    val gasolineList by listViewModel.gasList.observeAsState(listOf())
    val gasolineListByGasAndTown by listViewModel.gasListByGasAndTown.observeAsState(listOf())
    val mapProperties by mapViewModel.mapProperties.observeAsState(MapProperties(mapType = MapType.NORMAL))

    val modoCarburante = listViewModel.selectedGasolineId != ""
    val stations: List<StationUi> = (if (modoCarburante) {
        gasolineListByGasAndTown.map { it.toStationUi(listViewModel.selectedGasolineName) }
    } else {
        gasolineList.map { it.toStationUi() }
    }).filter { it.latitud != null && it.longitud != null }

    val precioMinimo = if (modoCarburante) stations.mapNotNull { it.precio }.minOrNull() else null
    val masBarata: StationUi? = precioMinimo?.let { min -> stations.firstOrNull { it.precio == min } }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(40.4165000, -3.7025600), 5.3f)
    }

    //ENCUADRA TODAS LAS ESTACIONES CUANDO CAMBIA LA BUSQUEDA
    LaunchedEffect(stations.map { it.id }) {
        if (stations.size == 1) {
            val unica = stations.first()
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(LatLng(unica.latitud!!, unica.longitud!!), 15f),
                1000
            )
        } else if (stations.isNotEmpty()) {
            val builder = LatLngBounds.builder()
            stations.forEach { builder.include(LatLng(it.latitud!!, it.longitud!!)) }
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(builder.build(), PADDING_MAP),
                1000
            )
        }
    }

    Column(Modifier.fillMaxSize()) {
        AdBanner(adUnitId = "ca-app-pub-4979320410432560/1231126890")
        Box(Modifier.weight(1f)) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = mapProperties,
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                stations.forEach { station ->
                    val esMasBarata = station.id == masBarata?.id
                    MarkerComposable(
                        keys = arrayOf<Any>(station.id, esMasBarata),
                        state = rememberMarkerState(
                            key = station.id,
                            position = LatLng(station.latitud!!, station.longitud!!)
                        ),
                        title = station.rotulo,
                        onClick = {
                            listViewModel.selectStation(station.id)
                            true
                        }
                    ) {
                        PricePin(precio = station.precio, enVerde = esMasBarata)
                    }
                }
            }

            //HEADER FLOTANTE
            if (stations.isNotEmpty()) {
                MapaHeader(
                    municipio = listViewModel.selectedTownName,
                    carburante = if (modoCarburante) listViewModel.selectedGasolineName else "Todos los carburantes",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp)
                )
            }

            //PREVIEW DE LA ESTACION MAS BARATA (O LA PRIMERA)
            val preview = masBarata ?: stations.firstOrNull()
            if (preview != null) {
                StationCard(
                    station = preview,
                    esMasBarata = preview.id == masBarata?.id,
                    onClick = { listViewModel.selectStation(preview.id) },
                    onToggleFavorito = { },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                )
            }
        }
    }
}

//PIN DE PRECIO: BURBUJA BLANCA (O VERDE SI ES LA MAS BARATA) CON FLECHITA INFERIOR
@Composable
private fun PricePin(precio: Double?, enVerde: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(if (enVerde) Verde else Superficie)
                .border(
                    1.dp,
                    if (enVerde) Verde else Linea,
                    RoundedCornerShape(999.dp)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = precio?.let { "${formatPrecio(it)} €" } ?: "—",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (enVerde) Color.White else Ink
            )
        }
        //FLECHITA
        Box(
            Modifier
                .size(width = 10.dp, height = 6.dp)
                .clip(TrianguloShape)
                .background(if (enVerde) Verde else Superficie)
        )
    }
}

private val TrianguloShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width / 2f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
private fun MapaHeader(municipio: String, carburante: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .shadow(6.dp, RoundedCornerShape(16.dp), spotColor = Color.Black.copy(alpha = 0.3f))
            .clip(RoundedCornerShape(16.dp))
            .background(Superficie)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_marker),
            contentDescription = null,
            tint = Naranja,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = "MOSTRANDO",
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 9.5.sp,
                color = Muted2
            )
            Text(
                text = if (municipio.isNotEmpty()) "$municipio · $carburante" else carburante,
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 12.5.sp,
                color = Ink,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

package com.jarica.preciogasolina.ui.ui.Detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.*
import com.jarica.preciogasolina.ui.ui.Components.BadgeMasBarata
import com.jarica.preciogasolina.ui.ui.Components.FavStar
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.FavScreen.FavoriteUiState
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.model.FuelPrice
import com.jarica.preciogasolina.ui.ui.model.StationUi
import com.jarica.preciogasolina.ui.ui.model.formatPrecio
import com.jarica.preciogasolina.ui.ui.model.parsePrecio
import com.jarica.preciogasolina.ui.ui.model.toStationUi

//DETALLE DE ESTACION: OVERLAY A PANTALLA COMPLETA SOBRE LA MAIN SCREEN
@Composable
fun StationDetailUi(
    stationId: String,
    listViewModel: ListViewModel,
    favViewModel: FavViewModel
) {

    BackHandler { listViewModel.selectStation(null) }

    val gasListByGasAndTown by listViewModel.gasListByGasAndTown.observeAsState(listOf())

    //ESTACION COMPLETA (TODOS LOS PRECIOS) SI ESTA DISPONIBLE; SI NO, LA VARIANTE DE UN SOLO CARBURANTE
    val station: StationUi? = remember(stationId, gasListByGasAndTown) {
        listViewModel.findStationById(stationId)?.toStationUi()
            ?: gasListByGasAndTown.find { it.iDEESS == stationId }
                ?.toStationUi(listViewModel.selectedGasolineName)
    }

    if (station == null) {
        listViewModel.selectStation(null)
        return
    }

    //FAVORITOS
    val listFavId: MutableList<String> = mutableListOf()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<FavoriteUiState>(
        initialValue = FavoriteUiState.Loading, key1 = lifecycle, key2 = favViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
            favViewModel.uiState.collect { value = it }
        }
    }
    when (uiState) {
        is FavoriteUiState.Success -> (uiState as FavoriteUiState.Success).favorites.forEach {
            listFavId.add(it.id)
        }
        else -> {}
    }
    val esFavorita = listFavId.contains(station.id)

    //PRECIO PRINCIPAL: EL CARBURANTE BUSCADO SI LO HAY, SI NO EL PRIMERO DISPONIBLE
    val carburanteBuscado = listViewModel.selectedGasolineName
    val precioPrincipal: FuelPrice? = station.precios.firstOrNull { it.nombre == carburanteBuscado }
        ?: station.precios.firstOrNull()
    val restoPrecios = station.precios.filter { it != precioPrincipal }

    //ES LA MAS BARATA DE LA BUSQUEDA ACTUAL (SOLO COMPARABLE EN MODO CARBURANTE)
    val esMasBarata = remember(stationId, gasListByGasAndTown) {
        val precios = gasListByGasAndTown.mapNotNull { parsePrecio(it.precioProducto) }
        val propio = gasListByGasAndTown.find { it.iDEESS == stationId }
            ?.let { parsePrecio(it.precioProducto) }
        precios.isNotEmpty() && propio != null && propio == precios.min()
    }

    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .background(Fondo)
    ) {

        //CABECERA CON MAPA
        MapHeader(station, Modifier.height(296.dp))

        //CONTENIDO SCROLLABLE QUE SOLAPA LA CABECERA
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(270.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                    .background(Fondo)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(20.dp))
                Text(
                    text = station.rotulo,
                    fontFamily = Sora,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 23.sp,
                    color = Ink
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${station.municipio} · ${station.provincia}",
                    fontFamily = Sora,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.5.sp,
                    color = Muted
                )
                Spacer(Modifier.height(16.dp))

                PanelPrecios(precioPrincipal, restoPrecios, esMasBarata)

                Spacer(Modifier.height(14.dp))

                PanelInfo(station)

                Spacer(Modifier.height(120.dp))
            }
        }

        //BOTONES CIRCULARES SUPERIORES
        Row(
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            BotonCircular(onClick = { listViewModel.selectStation(null) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Volver",
                    tint = Ink
                )
            }
            Spacer(Modifier.weight(1f))
            BotonCircular(onClick = {
                if (esFavorita) listViewModel.deleteFavorite(station.id)
                else listViewModel.addFavorite(station.id)
            }) {
                Icon(
                    painter = painterResource(
                        id = if (esFavorita) R.drawable.ic_starfilled_24 else R.drawable.ic_starempty_24
                    ),
                    contentDescription = "Favorito",
                    tint = if (esFavorita) Naranja else Muted3,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        //CTA NAVEGAR
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Fondo)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Button(
                onClick = { lanzarNavegacion(station, context) },
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Naranja,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(8.dp, RoundedCornerShape(18.dp), spotColor = Naranja.copy(alpha = 0.6f))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigation),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Navegar",
                    fontFamily = Sora,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.5.sp
                )
            }
        }
    }
}

@Composable
private fun MapHeader(station: StationUi, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth()) {
        if (station.latitud != null && station.longitud != null) {
            val posicion = LatLng(station.latitud, station.longitud)
            val cameraPositionState = rememberCameraPositionState(key = station.id) {
                position = CameraPosition.fromLatLngZoom(posicion, 16f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    scrollGesturesEnabled = false,
                    zoomGesturesEnabled = false,
                    tiltGesturesEnabled = false,
                    rotationGesturesEnabled = false
                )
            ) {
                Marker(
                    state = MarkerState(position = posicion),
                    title = station.rotulo
                )
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Linea)
            )
        }
    }
}

@Composable
private fun BotonCircular(onClick: () -> Unit, contenido: @Composable () -> Unit) {
    Box(
        Modifier
            .size(42.dp)
            .shadow(4.dp, CircleShape, spotColor = Color.Black.copy(alpha = 0.35f))
            .clip(CircleShape)
            .background(Superficie)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        contenido()
    }
}

@Composable
private fun PanelPrecios(
    precioPrincipal: FuelPrice?,
    restoPrecios: List<FuelPrice>,
    esMasBarata: Boolean
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Superficie)
            .padding(18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = (precioPrincipal?.nombre ?: "Precio").uppercase(),
                    fontFamily = Sora,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp,
                    letterSpacing = 0.08.em,
                    color = Muted2
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = precioPrincipal?.let { formatPrecio(it.precio) } ?: "—",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 38.sp,
                        lineHeight = 38.sp,
                        color = if (esMasBarata) Verde else Ink
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = "€",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = if (esMasBarata) Verde else Ink,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
            if (esMasBarata) {
                BadgeMasBarata()
            }
        }

        if (restoPrecios.isNotEmpty()) {
            Spacer(Modifier.height(14.dp))
            HorizontalDivider(thickness = 1.dp, color = Linea)
            Spacer(Modifier.height(14.dp))

            //GRID DE 2 COLUMNAS CON EL RESTO DE CARBURANTES
            restoPrecios.chunked(2).forEach { fila ->
                Row(Modifier.fillMaxWidth()) {
                    fila.forEach { fuel ->
                        Row(
                            Modifier
                                .weight(1f)
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = fuel.nombre,
                                fontFamily = Sora,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.5.sp,
                                color = InkSuave,
                                maxLines = 1,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "${formatPrecio(fuel.precio)} €",
                                fontFamily = SpaceGrotesk,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Ink
                            )
                            Spacer(Modifier.width(12.dp))
                        }
                    }
                    if (fila.size == 1) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun PanelInfo(station: StationUi) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Superficie)
            .padding(horizontal = 18.dp, vertical = 6.dp)
    ) {
        FilaInfo(icono = R.drawable.ic_marker, etiqueta = "DIRECCIÓN", valor = station.direccion)
        HorizontalDivider(thickness = 1.dp, color = Linea)
        FilaInfo(icono = R.drawable.ic_clock, etiqueta = "HORARIO", valor = station.horario)
        HorizontalDivider(thickness = 1.dp, color = Linea)
        FilaInfo(icono = R.drawable.ic_map, etiqueta = "LOCALIDAD", valor = station.localidad)
    }
}

@Composable
private fun FilaInfo(icono: Int, etiqueta: String, valor: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(NaranjaSuave),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icono),
                contentDescription = null,
                tint = Naranja,
                modifier = Modifier.size(17.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = etiqueta,
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 10.5.sp,
                letterSpacing = 0.1.em,
                color = Muted2
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = valor.ifEmpty { "—" },
                fontFamily = Sora,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.5.sp,
                color = Ink
            )
        }
    }
}

private fun lanzarNavegacion(station: StationUi, context: Context) {
    val latitud = station.latitud ?: return
    val longitud = station.longitud ?: return
    val gmmIntentUri = Uri.parse("google.navigation:q=$latitud,$longitud")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    ContextCompat.startActivity(context, mapIntent, bundleOf())
}

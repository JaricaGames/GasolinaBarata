package com.jarica.preciogasolina.ui.ui.List

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.jarica.preciogasolina.ui.theme.*
import com.jarica.preciogasolina.ui.ui.Components.AdBanner
import com.jarica.preciogasolina.ui.ui.Components.StationCard
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.FavScreen.FavoriteUiState
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations
import com.jarica.preciogasolina.ui.ui.model.StationUi
import com.jarica.preciogasolina.ui.ui.model.formatPrecio
import com.jarica.preciogasolina.ui.ui.model.toStationUi


@Composable
fun ListUi(
    listViewModel: ListViewModel, navController: NavHostController, favViewModel: FavViewModel
) {

    val gasList by listViewModel.gasList.observeAsState(listOf())
    val gasListByGasAndTown by listViewModel.gasListByGasAndTown.observeAsState(listOf())

    val listFavId: MutableList<String> = mutableListOf()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<FavoriteUiState>(
        initialValue = FavoriteUiState.Loading, key1 = lifecycle, key2 = favViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
            favViewModel.uiState.collect {
                value = it
            }
        }
    }

    when (uiState) {
        is FavoriteUiState.Error -> {}
        FavoriteUiState.Loading -> {}
        is FavoriteUiState.Success -> {
            (uiState as FavoriteUiState.Success).favorites.forEach {
                listFavId.add(it.id)
            }
        }
    }

    //MODO CARBURANTE: LA BUSQUEDA SE HIZO CON UN CARBURANTE CONCRETO Y LOS PRECIOS SON COMPARABLES
    val modoCarburante = listViewModel.selectedGasolineId != ""
    val stations: List<StationUi> = if (modoCarburante) {
        gasListByGasAndTown.map { it.toStationUi(listViewModel.selectedGasolineName) }
    } else {
        gasList.map { it.toStationUi() }
    }

    if (stations.isEmpty()) {
        EmptyGasStationList(navController)
        return
    }

    val ordenadas = if (modoCarburante) {
        stations.sortedWith(compareBy(nullsLast()) { it.precio })
    } else {
        stations
    }
    val precioMinimo = if (modoCarburante) stations.mapNotNull { it.precio }.minOrNull() else null
    val idMasBarata = precioMinimo?.let { min -> ordenadas.firstOrNull { it.precio == min }?.id }

    Column(Modifier.fillMaxSize()) {

        ListHeader(
            total = stations.size,
            carburante = if (modoCarburante) listViewModel.selectedGasolineName else "Todos los carburantes",
            municipio = listViewModel.selectedTownName,
            precioMinimo = precioMinimo,
            onFiltrosClick = { navController.navigate(Destinations.SearchScreen.route) }
        )

        AdBanner(adUnitId = "ca-app-pub-4979320410432560/2352636871")

        LazyColumn(
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(ordenadas, key = { it.id }) { station ->
                StationCard(
                    station = station,
                    esMasBarata = station.id == idMasBarata,
                    todosLosPrecios = !modoCarburante,
                    delta = if (precioMinimo != null && station.precio != null && station.id != idMasBarata) {
                        station.precio - precioMinimo
                    } else null,
                    esFavorita = listFavId.contains(station.id),
                    onClick = { listViewModel.selectStation(station.id) },
                    onToggleFavorito = {
                        if (listFavId.contains(station.id)) {
                            listViewModel.deleteFavorite(station.id)
                        } else {
                            listViewModel.addFavorite(station.id)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ListHeader(
    total: Int,
    carburante: String,
    municipio: String,
    precioMinimo: Double?,
    onFiltrosClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Superficie)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Gasolineras",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (total == 1) "1 estación" else "$total estaciones",
                fontFamily = Sora,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.5.sp,
                color = Muted
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
            ChipFiltro(texto = carburante, onClick = onFiltrosClick)
            if (municipio.isNotEmpty()) {
                ChipFiltro(texto = municipio, onClick = onFiltrosClick)
            }
            if (precioMinimo != null) {
                Text(
                    text = "Más barata · ${formatPrecio(precioMinimo)} €",
                    fontFamily = Sora,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp,
                    color = Verde,
                    maxLines = 1,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(VerdeFondo)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = Linea)
}

@Composable
private fun ChipFiltro(texto: String, onClick: () -> Unit) {
    Text(
        text = texto,
        fontFamily = Sora,
        fontWeight = FontWeight.Bold,
        fontSize = 11.5.sp,
        color = Ink,
        maxLines = 1,
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(Fondo)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    )
}

@Composable
fun EmptyGasStationList(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(NaranjaSuave),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Naranja,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "No hemos encontrado resultados",
            fontFamily = Sora,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Ink,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Prueba con otro carburante u otro municipio.",
            fontFamily = Sora,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Muted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.size(24.dp))
        Button(
            onClick = { navController.navigate(Destinations.SearchScreen.route) },
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Naranja,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text(
                text = "Modificar filtros",
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

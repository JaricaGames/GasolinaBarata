package com.jarica.preciogasolina.ui.ui.List

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jarica.preciogasolina.BuildConfig
import com.jarica.preciogasolina.ui.theme.*
import com.jarica.preciogasolina.ui.ui.Components.AdBanner
import com.jarica.preciogasolina.ui.ui.Components.EmptyState
import com.jarica.preciogasolina.ui.ui.Components.StationCard
import com.jarica.preciogasolina.ui.ui.Components.rememberFavoriteIds
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations
import com.jarica.preciogasolina.ui.ui.model.formatPrecio


@Composable
fun ListUi(
    listViewModel: ListViewModel, navController: NavHostController, favViewModel: FavViewModel
) {

    val results by listViewModel.searchResults.observeAsState(SearchResultsUiState())
    val listFavId = rememberFavoriteIds(favViewModel)

    if (results.stations.isEmpty()) {
        EmptyGasStationList(navController)
        return
    }

    val precioMinimo = results.precioMinimo

    Column(Modifier.fillMaxSize()) {

        ListHeader(
            total = results.stations.size,
            carburante = results.carburante.ifEmpty { "Todos los carburantes" },
            municipio = results.municipio,
            precioMinimo = precioMinimo,
            onFiltrosClick = { navController.navigate(Destinations.SearchScreen.route) }
        )

        AdBanner(adUnitId = BuildConfig.AD_UNIT_LISTA)

        LazyColumn(
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(results.stations, key = { it.id }) { station ->
                StationCard(
                    station = station,
                    esMasBarata = station.id == results.idMasBarata,
                    delta = if (precioMinimo != null && station.precio != null && station.id != results.idMasBarata) {
                        station.precio - precioMinimo
                    } else null,
                    todosLosPrecios = !results.esModoCarburante,
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
    EmptyState(
        icono = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Naranja,
                modifier = Modifier.size(40.dp)
            )
        },
        titulo = "No hemos encontrado resultados",
        texto = "Prueba con otro carburante u otro municipio.",
        textoBoton = "Modificar filtros",
        onBoton = { navController.navigate(Destinations.SearchScreen.route) }
    )
}

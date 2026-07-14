package com.jarica.preciogasolina.ui.ui.FavScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jarica.preciogasolina.BuildConfig
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.*
import com.jarica.preciogasolina.ui.ui.Components.AdBanner
import com.jarica.preciogasolina.ui.ui.Components.EmptyState
import com.jarica.preciogasolina.ui.ui.Components.StationCard
import com.jarica.preciogasolina.ui.ui.Components.rememberFavoriteUiState
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations
import com.jarica.preciogasolina.ui.ui.model.StationUi
import com.jarica.preciogasolina.ui.ui.model.toStationUi

@Composable
fun FavUi(
    favViewModel: FavViewModel,
    listViewModel: ListViewModel,
    navController: NavHostController
) {

    val uiState = rememberFavoriteUiState(favViewModel)
    val stationListError by favViewModel.stationListError.collectAsState()

    when (uiState) {
        is FavoriteUiState.Error -> {}
        FavoriteUiState.Loading -> {}
        is FavoriteUiState.Success -> {
            val listFavId = uiState.favorites.map { it.id }
            val stations: List<StationUi> = listFavId.mapNotNull { id ->
                favViewModel.lookForGasStationFavoriteCard(id)?.toStationUi()
            }

            when {
                listFavId.isEmpty() -> EmptyFavoritos(onBuscar = {
                    navController.navigate(Destinations.SearchScreen.route)
                })

                //HAY FAVORITOS PERO LA LISTA NACIONAL FALLO: AVISO EN VEZ DE SPINNER ETERNO
                stations.isEmpty() && stationListError -> MensajeErrorFavoritos()

                //HAY FAVORITOS Y LA LISTA NACIONAL AUN SE ESTA DESCARGANDO
                stations.isEmpty() -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Naranja)
                }

                else -> Column(Modifier.fillMaxSize()) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(Superficie)
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Favoritos",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = if (stations.size == 1) "1 guardada" else "${stations.size} guardadas",
                                fontFamily = Sora,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.5.sp,
                                color = Muted
                            )
                        }
                    }
                    HorizontalDivider(thickness = 1.dp, color = Linea)

                    AdBanner(adUnitId = BuildConfig.AD_UNIT_FAVORITOS)

                    LazyColumn(
                        contentPadding = PaddingValues(
                            start = 14.dp, end = 14.dp, top = 8.dp, bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(stations, key = { it.id }) { station ->
                            StationCard(
                                station = station,
                                esFavorita = true,
                                todosLosPrecios = true,
                                onClick = { listViewModel.selectStation(station.id) },
                                onToggleFavorito = { listViewModel.deleteFavorite(station.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MensajeErrorFavoritos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No se han podido cargar tus favoritos",
            fontFamily = Sora,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Ink,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "No hemos podido descargar la lista de estaciones. Revisa tu conexión y vuelve a abrir la app.",
            fontFamily = Sora,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Muted,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyFavoritos(onBuscar: () -> Unit) {
    EmptyState(
        icono = {
            Icon(
                painter = painterResource(id = R.drawable.ic_starempty_24),
                contentDescription = null,
                tint = Naranja,
                modifier = Modifier.size(40.dp)
            )
        },
        titulo = "Aún no tienes favoritos",
        texto = "Guarda tus gasolineras habituales con la estrella y tenlas siempre a mano.",
        textoBoton = "Buscar gasolineras",
        onBoton = onBuscar
    )
}

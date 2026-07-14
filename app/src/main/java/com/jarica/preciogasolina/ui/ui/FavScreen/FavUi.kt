package com.jarica.preciogasolina.ui.ui.FavScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.*
import com.jarica.preciogasolina.ui.ui.Components.AdBanner
import com.jarica.preciogasolina.ui.ui.Components.StationCard
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
            val listFavId = (uiState as FavoriteUiState.Success).favorites.map { it.id }
            val stations: List<StationUi> = listFavId.mapNotNull { id ->
                favViewModel.lookForGasStationFavoriteCard(id)?.toStationUi()
            }

            if (listFavId.isEmpty()) {
                EmptyFavoritos(onBuscar = {
                    navController.navigate(Destinations.SearchScreen.route)
                })
            } else if (stations.isEmpty()) {
                //HAY FAVORITOS GUARDADOS PERO LA LISTA GENERAL DE ESTACIONES AUN SE ESTA DESCARGANDO
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Naranja)
                }
            } else {
                Column(Modifier.fillMaxSize()) {
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

                    AdBanner(adUnitId = "ca-app-pub-4979320410432560/8638843620")

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
private fun EmptyFavoritos(onBuscar: () -> Unit) {
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
                painter = painterResource(id = R.drawable.ic_starempty_24),
                contentDescription = null,
                tint = Naranja,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Aún no tienes favoritos",
            fontFamily = Sora,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Ink,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Guarda tus gasolineras habituales con la estrella y tenlas siempre a mano.",
            fontFamily = Sora,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Muted,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onBuscar,
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
                text = "Buscar gasolineras",
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

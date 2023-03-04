package com.jarica.preciogasolina.ui.ui.FavScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.List.Screens.cardStationByTowns

@Composable
fun FavUi(favViewModel: FavViewModel, listViewModel: ListViewModel) {

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
            listViewModel.getIdLisFavorite(listFavId)
            LazyColumn(Modifier.padding(top = 6.dp, bottom = 65.dp)) {
                items(1) {
                    favViewModel.ReturnListFavId(listFavId)
                    listFavId.forEach { idGasStationFav ->
                        var gasStation = favViewModel.lookForGasStationFavoriteCard(idGasStationFav)
                        if (gasStation != null) {
                            cardStationByTowns(gasStation, listViewModel, listFavId)
                        }
                    }
                }

            }
        }


    }
}


/*
    listadoGasolinera?.ListaEESSPrecio!!.forEach() { GasolineraPorMunicipio ->


            Column(Modifier.fillMaxWidth()) {

                cardStationByTowns(GasolineraPorMunicipio, listViewModel)

                BannerAdView()
            }


    }*/





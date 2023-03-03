package com.jarica.preciogasolina.ui.ui.FavScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.List.Screens.cardStationByTowns
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.listadoGasolinera

@Composable
fun FavUi(favViewModel: FavViewModel, listViewModel: ListViewModel) {

    val listFavId: MutableList<String> = mutableListOf()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<FavoriteUiState>(
        initialValue = FavoriteUiState.Loading, key1 = lifecycle, key2 = favViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
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
                var id = it.id
                listFavId.add(id)

            }
            LazyColumn( Modifier.padding(top = 6.dp, bottom = 65.dp)) {
                items(1) {
                    listFavId.forEach { idGasStationFav ->
                        var gasStation = LookForGasStationFavoriteCard(idGasStationFav)
                        if (gasStation != null) {

                            cardStationByTowns(gasStation, listViewModel)


                        }
                    }
                }

            }

            //LookForGasStationFavoriteCard(listFavId.)?.let { cardStationByTowns(it, listViewModel) }
            //LookForGasStationFavoriteCard(listFavId)
            Log.i("Nono", listFavId.toString())
        }


    }
}

@Composable
fun LookForGasStationFavoriteCard(idGasStationFav: String): GasolineraPorMunicipio? {
    var gasStation: GasolineraPorMunicipio? = null
    gasStation = listadoGasolinera!!.ListaEESSPrecio.find {
        it.iDEESS == idGasStationFav
    }

    return gasStation

}


/*
    listadoGasolinera?.ListaEESSPrecio!!.forEach() { GasolineraPorMunicipio ->


            Column(Modifier.fillMaxWidth()) {

                cardStationByTowns(GasolineraPorMunicipio, listViewModel)

                BannerAdView()
            }


    }*/





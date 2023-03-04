package com.jarica.preciogasolina.ui.ui.FavScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.poppins
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

            if (listFavId.isNotEmpty()) {
                LazyColumn(Modifier.padding(top = 6.dp, bottom = 65.dp)) {
                    items(1) {
                        favViewModel.ReturnListFavId(listFavId)
                        listFavId.forEach { idGasStationFav ->
                            val gasStation =
                                favViewModel.lookForGasStationFavoriteCard(idGasStationFav)
                            if (gasStation != null) {
                                cardStationByTowns(gasStation, listViewModel, listFavId)
                            }
                        }
                    }

                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Actualmente no tiene ningún favorito",
                        fontSize = 22.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.Naranja)
                    )
                }
            }

        }


    }
}



package com.jarica.preciogasolina.ui.ui.Components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.FavScreen.FavoriteUiState

//SUSCRIPCION AL ESTADO DE FAVORITOS LIGADA AL CICLO DE VIDA, COMPARTIDA POR
//LISTA, MAPA, DETALLE Y FAVORITOS (ANTES ESTABA COPIADA EN CADA PANTALLA)
@Composable
fun rememberFavoriteUiState(favViewModel: FavViewModel): FavoriteUiState {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<FavoriteUiState>(
        initialValue = FavoriteUiState.Loading, key1 = lifecycle, key2 = favViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            favViewModel.uiState.collect { value = it }
        }
    }
    return uiState
}

@Composable
fun rememberFavoriteIds(favViewModel: FavViewModel): List<String> =
    (rememberFavoriteUiState(favViewModel) as? FavoriteUiState.Success)
        ?.favorites?.map { it.id } ?: emptyList()

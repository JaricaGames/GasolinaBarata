package com.jarica.preciogasolina.ui.ui.FavScreen

import com.jarica.preciogasolina.ui.ui.model.FavoriteModel

sealed interface FavoriteUiState {

    object Loading : FavoriteUiState
    data class Error(val throwable: Throwable) : FavoriteUiState
    data class Success(val favorites:List<FavoriteModel>):FavoriteUiState

}
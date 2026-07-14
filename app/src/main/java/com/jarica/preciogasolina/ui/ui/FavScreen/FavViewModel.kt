package com.jarica.preciogasolina.ui.ui.FavScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.core.SearchSelectionState
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.domain.GetFavoritesUseCase
import com.jarica.preciogasolina.ui.ui.FavScreen.FavoriteUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    getFavoritesUseCase: GetFavoritesUseCase,
    private val selectionState: SearchSelectionState
) : ViewModel() {

    //SE COMBINA CON stationListFlow PARA QUE LA PANTALLA SE REFRESQUE CUANDO
    //TERMINE LA DESCARGA DE LA LISTA NACIONAL (ANTES SE QUEDABA EN EL SPINNER)
    val uiState: StateFlow<FavoriteUiState> =
        combine(getFavoritesUseCase(), selectionState.stationListFlow) { favorites, _ ->
            Success(favorites) as FavoriteUiState
        }
            .catch { emit(FavoriteUiState.Error(it)) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoriteUiState.Loading)

    //true SI LA DESCARGA DE LA LISTA NACIONAL FALLO (LOS FAVORITOS NO SE PUEDEN RESOLVER)
    val stationListError: StateFlow<Boolean> = selectionState.stationListError

    fun lookForGasStationFavoriteCard(idGasStationFav: String): GasolineraPorMunicipio? =
        selectionState.stationById(idGasStationFav)
}

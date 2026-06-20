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

    var listFavIdAux: MutableList<String>? = null

    fun ReturnListFavId(listFavId: MutableList<String>) {
        listFavIdAux = listFavId
    }

    val uiState: StateFlow<FavoriteUiState> = getFavoritesUseCase().map(::Success)
        .catch { FavoriteUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoriteUiState.Loading)


    fun lookForGasStationFavoriteCard(idGasStationFav: String): GasolineraPorMunicipio? {
        val stationList = selectionState.stationList
        if (stationList != null) {
            return stationList.ListaEESSPrecio.find {
                it.iDEESS == idGasStationFav
            }
        } else {
            return null
        }

    }
}


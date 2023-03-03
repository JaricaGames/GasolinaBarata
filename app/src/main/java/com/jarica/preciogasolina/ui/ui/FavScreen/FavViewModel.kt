package com.jarica.preciogasolina.ui.ui.FavScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.domain.AddFavoriteUseCase
import com.jarica.preciogasolina.domain.DeleteFavoriteUseCase
import com.jarica.preciogasolina.domain.GetFavoritesUseCase
import com.jarica.preciogasolina.ui.ui.FavScreen.FavoriteUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    val uiState: StateFlow<FavoriteUiState> = getFavoritesUseCase().map(::Success)
            .catch { FavoriteUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoriteUiState.Loading)
}
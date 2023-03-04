package com.jarica.preciogasolina.ui.ui.List

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.data.network.repositories.RetrofitRepository
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.domain.AddFavoriteUseCase
import com.jarica.preciogasolina.domain.DeleteFavoriteUseCase
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.idGasolinaSeleccionada
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.idMunicipioSeleccionado
import com.jarica.preciogasolina.ui.ui.model.FavoriteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
) : ViewModel() {


    private val _gasList = MutableLiveData<List<GasolineraPorMunicipio>>()
    val gasList: MutableLiveData<List<GasolineraPorMunicipio>> = _gasList


    private val _gasListByGasAndTown = MutableLiveData<List<GasolineraPorGasolinaYMunicipio>>()
    val gasListByGasAndTown: MutableLiveData<List<GasolineraPorGasolinaYMunicipio>> = _gasListByGasAndTown


    fun getGasStationsByTowns() {
        viewModelScope.launch {
            _gasList.value = retrofitRepository.getGasStationsByTowns(ID = idMunicipioSeleccionado)
        }

    }

    fun getGasStationsByTownsAndGasoline() {
        viewModelScope.launch {
            _gasListByGasAndTown.value =
                retrofitRepository.getGasStationsByTownsAndGasoline(
                    IDTown = idMunicipioSeleccionado,
                    IDGasoline = idGasolinaSeleccionada
                )


        }
    }

    fun addFavorite(iDEESS: String) {
        viewModelScope.launch {
            addFavoriteUseCase(FavoriteModel(id = iDEESS))
        }

    }
    fun deleteFavorite(iDEESS: String) {
        viewModelScope.launch {
            deleteFavoriteUseCase(FavoriteModel(id = iDEESS))
        }

    }

}
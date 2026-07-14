package com.jarica.preciogasolina.ui.ui.List

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.core.SearchSelectionState
import com.jarica.preciogasolina.data.network.repositories.RetrofitRepository
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.domain.AddFavoriteUseCase
import com.jarica.preciogasolina.domain.DeleteFavoriteUseCase
import com.jarica.preciogasolina.ui.ui.model.FavoriteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val selectionState: SearchSelectionState,
) : ViewModel() {


    private val _gasList = MutableLiveData<List<GasolineraPorMunicipio>>()
    val gasList: MutableLiveData<List<GasolineraPorMunicipio>> = _gasList


    private val _gasListByGasAndTown = MutableLiveData<List<GasolineraPorGasolinaYMunicipio>>()
    val gasListByGasAndTown: MutableLiveData<List<GasolineraPorGasolinaYMunicipio>> = _gasListByGasAndTown

    // Expuesto para la UI (ListUi / CardStationByGasolineAndTown) en lugar del antiguo estado estático.
    val selectedGasolineId: String get() = selectionState.gasolineId
    val selectedGasolineName: String get() = selectionState.gasolineName
    val selectedTownName: String get() = selectionState.townName

    //ESTACION ABIERTA EN LA PANTALLA DE DETALLE (null = detalle cerrado)
    private val _selectedStationId = MutableLiveData<String?>(null)
    val selectedStationId: MutableLiveData<String?> = _selectedStationId

    fun selectStation(id: String?) {
        _selectedStationId.value = id
    }

    //BUSCA LA ESTACION COMPLETA (TODOS LOS PRECIOS) EN LA LISTA GENERAL DE ESTACIONES
    fun findStationById(id: String): GasolineraPorMunicipio? =
        selectionState.stationList?.ListaEESSPrecio?.find { it.iDEESS == id }
            ?: _gasList.value?.find { it.iDEESS == id }


    fun getGasStationsByTowns() {
        viewModelScope.launch {
            _gasList.value = retrofitRepository.getGasStationsByTowns(ID = selectionState.townId)
        }

    }

    fun getGasStationsByTownsAndGasoline() {
        viewModelScope.launch {
            _gasListByGasAndTown.value =
                retrofitRepository.getGasStationsByTownsAndGasoline(
                    IDTown = selectionState.townId,
                    IDGasoline = selectionState.gasolineId
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
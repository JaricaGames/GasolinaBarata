package com.jarica.preciogasolina.ui.ui.List

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.data.network.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.data.network.MainRepository
import com.jarica.preciogasolina.data.network.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.idGasolinaSeleccionada
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.idMunicipioSeleccionado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _gasList = MutableLiveData<List<GasolineraPorMunicipio>>()
    val gasList: MutableLiveData<List<GasolineraPorMunicipio>> = _gasList

    private val _gasListByGasAndTown = MutableLiveData<List<GasolineraPorGasolinaYMunicipio>>()
    val gasListByGasAndTown: MutableLiveData<List<GasolineraPorGasolinaYMunicipio>> = _gasListByGasAndTown


    fun getGasStationsByTowns() {
        viewModelScope.launch {
            _gasList.value =
                mainRepository.getGasStationsByTowns(ID = idMunicipioSeleccionado)


        }


    }

    fun getGasStationsByTownsAndGasoline() {
        viewModelScope.launch {
            Log.i("Nono", "$idMunicipioSeleccionado $idGasolinaSeleccionada")
            _gasListByGasAndTown.value =
                mainRepository.getGasStationsByTownsAndGasoline(IDTown = idMunicipioSeleccionado, IDGasoline = idGasolinaSeleccionada)


        }
    }
}
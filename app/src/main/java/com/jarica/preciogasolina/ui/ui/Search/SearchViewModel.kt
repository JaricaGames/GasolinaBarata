package com.jarica.preciogasolina.ui.ui.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.data.network.Retrofit.response.Province
import com.jarica.preciogasolina.data.network.repositories.RetrofitRepository
import com.jarica.preciogasolina.data.network.Retrofit.response.Gasolina
import com.jarica.preciogasolina.data.network.Retrofit.response.MainResponse
import com.jarica.preciogasolina.data.network.Retrofit.response.Towns
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository) :
    ViewModel() {

    private val _gasolineSelected = MutableLiveData<String>()
    val gasolineSelected: LiveData<String> = _gasolineSelected

    private val _isGasolineExpanded = MutableLiveData<Boolean>()
    val isGasolineExpanded: LiveData<Boolean> = _isGasolineExpanded

    private val _gasolineList = MutableLiveData<List<Gasolina>>()
    val gasolineList: LiveData<List<Gasolina>> = _gasolineList

    private val _isGasolineSelected = MutableLiveData<Boolean>()


    private val _provinceSelected = MutableLiveData<String>()
    val provinceSelected: LiveData<String> = _provinceSelected

    private val _isProvinceExpanded = MutableLiveData<Boolean>()
    val isProvinceExpanded: LiveData<Boolean> = _isProvinceExpanded

    private val _provinceList = MutableLiveData<List<Province>>()
    val provincesList: LiveData<List<Province>> = _provinceList

    private val _isProvinceSelected = MutableLiveData<Boolean>()
    val isProvinceSelected: LiveData<Boolean> = _isProvinceSelected


    private val _townSelected = MutableLiveData<String>()
    val townSelected: LiveData<String> = _townSelected

    private val _isTownExpanded = MutableLiveData<Boolean>()
    val isTownExpanded: LiveData<Boolean> = _isTownExpanded

    private val _townsList = MutableLiveData<List<Towns>>()
    val townsList: LiveData<List<Towns>> = _townsList

    private val _isTownSelected = MutableLiveData<Boolean>()
    val isTownSelected: LiveData<Boolean> = _isTownSelected

    private val _isDataCharging = MutableLiveData<Boolean>()
    val isDataCharging: LiveData<Boolean> = _isDataCharging

    companion object {
        var idMunicipioSeleccionado = ""
        var idGasolinaSeleccionada = ""
        var nameGasolinaSeleccionada = ""
        var listadoGasolinera: MainResponse? = null
    }

    init {
        viewModelScope.launch {
            _isDataCharging.value = true
            _provinceList.value = retrofitRepository.getProvincias()
            _gasolineList.value = retrofitRepository.getGasolines()
            _isDataCharging.value = false
            listadoGasolinera = retrofitRepository.getEESS()
        }
    }

    fun onProvinceSelectedChanged(province: String) {
        _provinceSelected.value = province
    }

    fun isProvinceSelected(isExpanded: Boolean) {
        _isProvinceExpanded.value = !isExpanded
    }

    fun onProvinceSelected(label: String, idProvincia: String) {
        _provinceSelected.value = label
        _isProvinceSelected.value = true
        _townSelected.value = ""
        getTownsByProvince(idProvincia)
    }

    fun onTownClicked(townExpanded: Boolean) {
        _isTownExpanded.value = !townExpanded
    }

    fun onTownSelected(municipio: String, isTownSelected: Boolean, idMunicipio: String) {
        _townSelected.value = municipio
        if (!isTownSelected) _isTownSelected.value = !isTownSelected
        idMunicipioSeleccionado = idMunicipio

    }

    fun onDismissTown() {
        _townSelected.value = ""
        _isTownSelected.value = false
    }

    private fun getTownsByProvince(idProvincia: String) {
        viewModelScope.launch {
            _townsList.value = retrofitRepository.getTownsbyProvince(idProvincia)
        }
    }


    fun isGasolineSelected(isExpanded: Boolean) {
        _isGasolineExpanded.value = !isExpanded
    }

    fun onGasolineClicked(gasolineExpanded: Boolean) {
        _isGasolineExpanded.value = !gasolineExpanded
    }

    fun onGasolineSelected(iDProducto: String, nombreProducto: String) {
        _gasolineSelected.value = nombreProducto
        _isGasolineSelected.value = true
        idGasolinaSeleccionada = iDProducto
        nameGasolinaSeleccionada = nombreProducto
    }

    fun onDismissGasoline() {
        _gasolineSelected.value = ""
        idGasolinaSeleccionada = ""
        nameGasolinaSeleccionada = ""
    }

    fun onDismissProvince() {
        _provinceSelected.value = ""
        _isProvinceSelected.value = false
    }

}

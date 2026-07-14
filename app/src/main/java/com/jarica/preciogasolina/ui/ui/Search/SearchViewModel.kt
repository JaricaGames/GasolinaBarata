package com.jarica.preciogasolina.ui.ui.Search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.core.PreferencesManager
import com.jarica.preciogasolina.core.RecentSearch
import com.jarica.preciogasolina.core.SearchSelectionState
import com.jarica.preciogasolina.data.network.Retrofit.response.Province
import com.jarica.preciogasolina.data.network.repositories.RetrofitRepository
import com.jarica.preciogasolina.data.network.Retrofit.response.Gasolina
import com.jarica.preciogasolina.data.network.Retrofit.response.Towns
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    private val preferencesManager: PreferencesManager,
    private val selectionState: SearchSelectionState
) :
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

    private val _recentSearches = MutableLiveData<List<RecentSearch>>()
    val recentSearches: LiveData<List<RecentSearch>> = _recentSearches

    init {
        viewModelScope.launch {
            _isDataCharging.value = true
            _provinceList.value = retrofitRepository.getProvincias()
            _gasolineList.value = retrofitRepository.getGasolines()
            _isDataCharging.value = false
            selectionState.stationList = retrofitRepository.getEESS()

        }
        viewModelScope.launch {
            preferencesManager.loadRecentSearches().collect {
                _recentSearches.value = it
            }
        }
    }

    //GUARDA LA BUSQUEDA ACTUAL COMO RECIENTE, SE LLAMA AL PULSAR BUSCAR
    fun onSearchLaunched() {
        val town = _townSelected.value ?: return
        if (town.isEmpty()) return
        val search = RecentSearch(
            gasolineId = selectionState.gasolineId,
            gasolineName = selectionState.gasolineName,
            provinceId = selectionState.provinceId,
            provinceName = _provinceSelected.value ?: "",
            townId = selectionState.townId,
            townName = town
        )
        viewModelScope.launch {
            preferencesManager.saveRecentSearch(search)
        }
    }

    //RESTAURA LA SELECCION DE UNA BUSQUEDA RECIENTE ANTES DE LANZARLA
    fun onRecentSearchClicked(search: RecentSearch) {
        selectionState.provinceId = search.provinceId
        selectionState.townId = search.townId
        selectionState.townName = search.townName
        selectionState.gasolineId = search.gasolineId
        selectionState.gasolineName = search.gasolineName
        _gasolineSelected.value = search.gasolineName
        _provinceSelected.value = search.provinceName
        _isProvinceSelected.value = true
        _townSelected.value = search.townName
        _isTownSelected.value = true
        getTownsByProvince(search.provinceId)
        viewModelScope.launch {
            //LA VUELVE A GUARDAR PARA SUBIRLA A LA PRIMERA POSICION
            preferencesManager.saveRecentSearch(search)
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
        selectionState.provinceId = idProvincia
        _isProvinceSelected.value = true
        _townSelected.value = ""
        getTownsByProvince(idProvincia)
    }

    fun onTownClicked(townExpanded: Boolean) {
        _isTownExpanded.value = !townExpanded
        getTownsByProvince(selectionState.provinceId)
    }

    fun onTownSelected(municipio: String, isTownSelected: Boolean, idMunicipio: String) {
        _townSelected.value = municipio
        if (!isTownSelected) _isTownSelected.value = !isTownSelected
        selectionState.townId = idMunicipio
        selectionState.townName = municipio


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
        selectionState.gasolineId = iDProducto
        selectionState.gasolineName = nombreProducto
    }

    fun onDismissGasoline() {
        _gasolineSelected.value = ""
        selectionState.gasolineId = ""
        selectionState.gasolineName = ""


    }

    fun onDismissProvince() {
        _provinceSelected.value = ""
        _isProvinceSelected.value = false

    }


}


package com.jarica.preciogasolina.ui.ui.Search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.core.PreferencesManager
import com.jarica.preciogasolina.core.RecentSearch
import com.jarica.preciogasolina.core.SearchSelectionState
import com.jarica.preciogasolina.data.network.Retrofit.response.Gasolina
import com.jarica.preciogasolina.data.network.Retrofit.response.Province
import com.jarica.preciogasolina.data.network.Retrofit.response.Towns
import com.jarica.preciogasolina.data.network.repositories.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    private val preferencesManager: PreferencesManager,
    private val selectionState: SearchSelectionState
) : ViewModel() {

    private val _gasolineSelected = MutableLiveData<String>()
    val gasolineSelected: LiveData<String> = _gasolineSelected

    private val _gasolineList = MutableLiveData<List<Gasolina>>()
    val gasolineList: LiveData<List<Gasolina>> = _gasolineList

    private val _provinceSelected = MutableLiveData<String>()
    val provinceSelected: LiveData<String> = _provinceSelected

    private val _provinceList = MutableLiveData<List<Province>>()
    val provincesList: LiveData<List<Province>> = _provinceList

    private val _townSelected = MutableLiveData<String>()
    val townSelected: LiveData<String> = _townSelected

    private val _townsList = MutableLiveData<List<Towns>>()
    val townsList: LiveData<List<Towns>> = _townsList

    private val _isDataCharging = MutableLiveData<Boolean>()
    val isDataCharging: LiveData<Boolean> = _isDataCharging

    private val _recentSearches = MutableLiveData<List<RecentSearch>>()
    val recentSearches: LiveData<List<RecentSearch>> = _recentSearches

    init {
        viewModelScope.launch {
            _isDataCharging.value = true
            try {
                _provinceList.value = retrofitRepository.getProvincias()
                _gasolineList.value = retrofitRepository.getGasolines()
            } finally {
                //AUNQUE FALLE LA RED, LA APP DEBE SALIR DEL SPLASH Y NO QUEDARSE CARGANDO PARA SIEMPRE
                _isDataCharging.value = false
            }
            //LISTA NACIONAL PARA FAVORITOS Y DETALLE; SU FALLO NO BLOQUEA LA BUSQUEDA NORMAL
            try {
                selectionState.stationList = retrofitRepository.getEESS()
            } catch (e: Exception) {
                selectionState.stationListError.value = true
            }
        }
        viewModelScope.launch {
            var prefilled = false
            preferencesManager.loadRecentSearches().collect {
                _recentSearches.value = it
                //AL ABRIR LA APP, LOS CAMPOS SE RELLENAN SOLOS CON LA ULTIMA BUSQUEDA GUARDADA
                if (!prefilled) {
                    prefilled = true
                    if (selectionState.townId.isEmpty() && selectionState.gasolineId.isEmpty()) {
                        it.firstOrNull()?.let(::applySearchSelection)
                    }
                }
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
        applySearchSelection(search)
        viewModelScope.launch {
            //LA VUELVE A GUARDAR PARA SUBIRLA A LA PRIMERA POSICION
            preferencesManager.saveRecentSearch(search)
        }
    }

    //VUELCA UNA BUSQUEDA GUARDADA EN LOS CAMPOS Y EN EL ESTADO COMPARTIDO
    private fun applySearchSelection(search: RecentSearch) {
        selectionState.provinceId = search.provinceId
        selectionState.townId = search.townId
        selectionState.townName = search.townName
        selectionState.gasolineId = search.gasolineId
        selectionState.gasolineName = search.gasolineName
        _gasolineSelected.value = search.gasolineName
        _provinceSelected.value = search.provinceName
        _townSelected.value = search.townName
        getTownsByProvince(search.provinceId)
    }

    fun onProvinceSelected(label: String, idProvincia: String) {
        _provinceSelected.value = label
        selectionState.provinceId = idProvincia
        _townSelected.value = ""
        selectionState.townId = ""
        selectionState.townName = ""
        getTownsByProvince(idProvincia)
    }

    fun onTownSelected(municipio: String, idMunicipio: String) {
        _townSelected.value = municipio
        selectionState.townId = idMunicipio
        selectionState.townName = municipio
    }

    private fun getTownsByProvince(idProvincia: String) {
        viewModelScope.launch {
            _townsList.value = retrofitRepository.getTownsbyProvince(idProvincia)
        }
    }

    fun onGasolineSelected(iDProducto: String, nombreProducto: String) {
        _gasolineSelected.value = nombreProducto
        selectionState.gasolineId = iDProducto
        selectionState.gasolineName = nombreProducto
    }

    fun onDismissGasoline() {
        _gasolineSelected.value = ""
        selectionState.gasolineId = ""
        selectionState.gasolineName = ""
    }
}

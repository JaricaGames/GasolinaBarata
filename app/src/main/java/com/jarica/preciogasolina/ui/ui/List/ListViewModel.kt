package com.jarica.preciogasolina.ui.ui.List

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.preciogasolina.core.PreferencesManager
import com.jarica.preciogasolina.core.SearchSelectionState
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.data.network.repositories.RetrofitRepository
import com.jarica.preciogasolina.domain.AddFavoriteUseCase
import com.jarica.preciogasolina.domain.DeleteFavoriteUseCase
import com.jarica.preciogasolina.ui.ui.model.FavoriteModel
import com.jarica.preciogasolina.ui.ui.model.StationUi
import com.jarica.preciogasolina.ui.ui.model.nombreCarburanteCanonico
import com.jarica.preciogasolina.ui.ui.model.toStationUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LITROS_DEPOSITO = 50

/**
 * Resultado de la ULTIMA BUSQUEDA EJECUTADA, capturado al pulsar Buscar.
 * Lista, Mapa y Detalle consumen solo este estado: así el modo, el municipio,
 * la "más barata" y el ahorro se corresponden siempre con la misma búsqueda,
 * aunque el usuario cambie selecciones en Buscar sin volver a buscar.
 */
data class SearchResultsUiState(
    val stations: List<StationUi> = emptyList(),
    val carburante: String = "",      // nombre canónico del carburante buscado; "" = todos
    val municipio: String = "",
    val esModoCarburante: Boolean = false,
    val idMasBarata: String? = null,
    val precioMinimo: Double? = null,
    val ahorroDeposito: Double? = null
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val retrofitRepository: RetrofitRepository,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val selectionState: SearchSelectionState,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    private val _searchResults = MutableLiveData(SearchResultsUiState())
    val searchResults: LiveData<SearchResultsUiState> = _searchResults

    //EVENTO DE UN SOLO USO: true CUANDO TOCA PEDIR LA VALORACION EN GOOGLE PLAY
    private val _askForReview = MutableLiveData(false)
    val askForReview: LiveData<Boolean> = _askForReview

    fun onReviewLaunched() {
        _askForReview.value = false
    }

    //ULTIMA BUSQUEDA POR MUNICIPIO CON TODOS LOS PRECIOS, PARA RESOLVER LA FICHA DE DETALLE
    private var lastTownStations: List<GasolineraPorMunicipio> = emptyList()

    //ESTACION ABIERTA EN LA PANTALLA DE DETALLE (null = detalle cerrado)
    private val _selectedStationId = MutableLiveData<String?>(null)
    val selectedStationId: MutableLiveData<String?> = _selectedStationId

    fun selectStation(id: String?) {
        _selectedStationId.value = id
    }

    fun getGasStationsByTowns() {
        viewModelScope.launch {
            val respuesta = retrofitRepository.getGasStationsByTowns(ID = selectionState.townId)
            lastTownStations = respuesta
            val stations = respuesta.map { it.toStationUi() }
            _searchResults.value = SearchResultsUiState(
                stations = stations,
                municipio = selectionState.townName,
                //EN MODO "TODOS" EL AHORRO SE CALCULA SOBRE GASOLEO A, EL UNICO COMPARABLE ENTRE ESTACIONES
                ahorroDeposito = ahorroDeposito(
                    stations.mapNotNull { st -> st.precios.find { it.nombre == "Gasóleo A" }?.precio }
                )
            )
            registerSearchForReview()
        }
    }

    fun getGasStationsByTownsAndGasoline() {
        viewModelScope.launch {
            val respuesta = retrofitRepository.getGasStationsByTownsAndGasoline(
                IDTown = selectionState.townId,
                IDGasoline = selectionState.gasolineId
            )
            val stations = respuesta.map { it.toStationUi(selectionState.gasolineName) }
                .sortedWith(compareBy(nullsLast()) { it.precio })
            val minimo = stations.mapNotNull { it.precio }.minOrNull()
            _searchResults.value = SearchResultsUiState(
                stations = stations,
                carburante = nombreCarburanteCanonico(selectionState.gasolineName),
                municipio = selectionState.townName,
                esModoCarburante = true,
                idMasBarata = minimo?.let { min -> stations.firstOrNull { it.precio == min }?.id },
                precioMinimo = minimo,
                ahorroDeposito = ahorroDeposito(stations.mapNotNull { it.precio })
            )
            registerSearchForReview()
        }
    }

    //SOLO CUENTAN LAS BUSQUEDAS QUE LLEGAN A MOSTRAR RESULTADOS (SI LA RED FALLA, NO SE LLEGA AQUI)
    private suspend fun registerSearchForReview() {
        if (preferencesManager.registerSearchForReview()) {
            _askForReview.value = true
        }
    }

    //AHORRO POR DEPOSITO = (PRECIO MAX - PRECIO MIN) * LITROS; null SI NO HAY DATOS COMPARABLES
    private fun ahorroDeposito(precios: List<Double>): Double? {
        if (precios.size < 2) return null
        val ahorro = (precios.max() - precios.min()) * LITROS_DEPOSITO
        return if (ahorro > 0.005) ahorro else null
    }

    //BUSCA LA ESTACION COMPLETA (TODOS LOS PRECIOS) PARA LA FICHA DE DETALLE
    fun findStationById(id: String): GasolineraPorMunicipio? =
        selectionState.stationById(id) ?: lastTownStations.find { it.iDEESS == id }

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

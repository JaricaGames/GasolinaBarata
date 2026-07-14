package com.jarica.preciogasolina.core

import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.data.network.Retrofit.response.MainResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Estado de selección compartido entre las pantallas Search, List y Fav.
 *
 * Sustituye al antiguo `companion object` estático de [com.jarica.preciogasolina.ui.ui.Search.SearchViewModel].
 * Al estar en el `ActivityRetainedComponent` comparte el mismo ciclo de vida que los ViewModels
 * (se libera cuando la Activity termina de verdad), evitando estado estático que sobrevive al
 * proceso y fugas de [MainResponse].
 */
@ActivityRetainedScoped
class SearchSelectionState @Inject constructor() {
    var provinceId: String = ""
    var townId: String = ""
    var townName: String = ""
    var gasolineId: String = ""
    var gasolineName: String = ""

    //LISTA NACIONAL DE ESTACIONES: FLOW PARA QUE LA UI PUEDA REACCIONAR CUANDO TERMINE LA DESCARGA
    val stationListFlow = MutableStateFlow<MainResponse?>(null)
    val stationListError = MutableStateFlow(false)

    var stationList: MainResponse?
        get() = stationListFlow.value
        set(value) {
            stationsById = null
            stationListFlow.value = value
        }

    //INDICE POR IDEESS PARA NO RECORRER ~11.500 ESTACIONES EN CADA BUSQUEDA
    private var stationsById: Map<String, GasolineraPorMunicipio>? = null

    fun stationById(id: String): GasolineraPorMunicipio? {
        val lista = stationList?.ListaEESSPrecio ?: return null
        val cache = stationsById ?: lista.associateBy { it.iDEESS }.also { stationsById = it }
        return cache[id]
    }
}

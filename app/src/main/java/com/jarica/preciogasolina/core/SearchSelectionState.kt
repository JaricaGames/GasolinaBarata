package com.jarica.preciogasolina.core

import com.jarica.preciogasolina.data.network.Retrofit.response.MainResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
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
    var gasolineId: String = ""
    var gasolineName: String = ""
    var stationList: MainResponse? = null
}

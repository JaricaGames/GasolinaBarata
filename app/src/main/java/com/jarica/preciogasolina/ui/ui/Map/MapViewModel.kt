package com.jarica.preciogasolina.ui.ui.Map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.jarica.preciogasolina.data.network.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.data.network.response.GasolineraPorMunicipio
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): ViewModel() {

    private val _mapProperties = MutableLiveData<MapProperties>()
    val mapProperties: LiveData<MapProperties> = _mapProperties

    private val _cameraPositionState = MutableLiveData<CameraPositionState>()
    val cameraPositionState: LiveData<CameraPositionState> = _cameraPositionState

    private val _cameraPosition = MutableLiveData<LatLng>()
    val cameraPosition: LiveData<LatLng> = _cameraPosition


    fun replaceString(coordenada: String): Double {
        var coordenadaOK = coordenada.replace(",", ".", false)
        return coordenadaOK.toDouble()
    }

    fun getCoordenatesByTown(gasolineList: List<GasolineraPorMunicipio>){

        var latitud = replaceString(gasolineList[0].latitud)
        var longitud = replaceString(gasolineList[0].longitud)
        _cameraPositionState.value = CameraPositionState(position = CameraPosition.fromLatLngZoom(LatLng(latitud, longitud),12f) )
    }

    fun getCoordenatesByGasAndTown(gasolineListByGasAndTown: List<GasolineraPorGasolinaYMunicipio>) {
        var latitud = replaceString(gasolineListByGasAndTown[0].latitud)
        var longitud = replaceString(gasolineListByGasAndTown[0].longitud)
        _cameraPositionState.value = CameraPositionState(position = CameraPosition.fromLatLngZoom(LatLng(latitud, longitud),12f) )
    }

    fun getCameraPosition(gasolineList: List<GasolineraPorMunicipio>): LatLng {
        return LatLng(
            replaceString(gasolineList[0].latitud),
            replaceString(gasolineList[0].longitud)
        )

    }

    var sWBound:LatLng = LatLng(100.0, 100.0)
    var nEBound:LatLng = LatLng(-100.0, -100.0)
    fun getBounds(latitud: Double, longitud: Double): LatLngBounds {

        if(latitud<sWBound.latitude) sWBound = LatLng(latitud, sWBound.longitude)
        if(longitud<sWBound.longitude) sWBound = LatLng(sWBound.latitude, longitud)
        if(latitud>nEBound.latitude) nEBound = LatLng(latitud, nEBound.longitude)
        if(longitud>nEBound.longitude) nEBound = LatLng(nEBound.latitude, longitud)

        return LatLngBounds(sWBound, nEBound)
   }

}
package com.jarica.preciogasolina.ui.ui.Map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.MapProperties
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): ViewModel() {

    private val _mapProperties = MutableLiveData<MapProperties>()
    val mapProperties: LiveData<MapProperties> = _mapProperties




    fun replaceString(coordenada: String): Double {
        val coordenadaOK = coordenada.replace(",", ".", false)
        return coordenadaOK.toDouble()
    }


    var sWBound = LatLng(100.0, 100.0)
    var nEBound = LatLng(-100.0, -100.0)


    fun getBounds(latitud: Double, longitud: Double): LatLngBounds {



        if(latitud<sWBound.latitude) sWBound = LatLng(latitud, sWBound.longitude)
        if(longitud<sWBound.longitude) sWBound = LatLng(sWBound.latitude, longitud)
        if(latitud>nEBound.latitude) nEBound = LatLng(latitud, nEBound.longitude)
        if(longitud>nEBound.longitude) nEBound = LatLng(nEBound.latitude, longitud)

        return LatLngBounds(sWBound, nEBound)
   }

    fun restartBounds() {
        sWBound = LatLng(100.0, 100.0)
        nEBound = LatLng(-100.0, -100.0)
    }

}
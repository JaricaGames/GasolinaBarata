package com.jarica.preciogasolina.ui.ui.Map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat.getDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.*
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




    fun replaceString(coordenada: String): Double {
        var coordenadaOK = coordenada.replace(",", ".", false)
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


    fun BitmapFromVector(context: Context, icGasstationdefault: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = getDrawable(context, icGasstationdefault)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

        // below line is use to create a bitmap for our drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun restartBounds() {
        sWBound = LatLng(100.0, 100.0)
        nEBound = LatLng(-100.0, -100.0)
    }

}
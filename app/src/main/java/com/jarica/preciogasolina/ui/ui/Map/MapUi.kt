package com.jarica.preciogasolina.ui.ui.Map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val PADDING_MAP = 100

@Composable
fun MapUi(mapViewModel: MapViewModel, listViewModel: ListViewModel) {


    val gasolineList by listViewModel.gasList.observeAsState(listOf())
    val gasolineListByGasAndTown by listViewModel.gasListByGasAndTown.observeAsState(listOf())
    val mapProperties by mapViewModel.mapProperties.observeAsState(MapProperties(mapType = MapType.NORMAL))
    val cameraPositionState2 = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(40.4165000, -3.7025600), 5.3f)
    }



    Column(
        Modifier.fillMaxSize(),
    ) {
        BannerAdView()
        MyGoogleMap(
            gasolineList,
            gasolineListByGasAndTown,
            mapViewModel,
            mapProperties,
            cameraPositionState2
        )
    }

}


@SuppressLint("MissingPermission")
@Composable
fun BannerAdView() {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-4979320410432560/7752668839"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
fun MyGoogleMap(
    gasolineList: List<GasolineraPorMunicipio>,
    gasolineListByGasAndTown: List<GasolineraPorGasolinaYMunicipio>,
    mapViewModel: MapViewModel,
    mapProperties: MapProperties,
    cameraPositionState: CameraPositionState
) {

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 55.dp),
        properties = mapProperties,
        cameraPositionState = cameraPositionState,
    ) {

        if (gasolineList.isNotEmpty()) {
            gasolineList.forEach { gasolinera ->

                val longitud = mapViewModel.replaceString(gasolinera.longitud)
                val latitud = mapViewModel.replaceString(gasolinera.latitud)

                val bounds = mapViewModel.getBounds(latitud, longitud)
                val position = LatLng(latitud, longitud)
                cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds, PADDING_MAP))

                Marker(
                    rememberMarkerState(position = position),
                    title = gasolinera.rotulo,
                    snippet = gasolinera.direccion
                )

            }
            mapViewModel.restartBounds()
        }
        if (gasolineListByGasAndTown.isNotEmpty()) {


            gasolineListByGasAndTown.forEach { gasolinera ->
                val longitud = mapViewModel.replaceString(gasolinera.longitud)
                val latitud = mapViewModel.replaceString(gasolinera.latitud)

                val bounds = mapViewModel.getBounds(latitud, longitud)
                val position = LatLng(latitud, longitud)

                CoroutineScope(Dispatchers.Main).launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            PADDING_MAP
                        ), 1000
                    )
                }

                Marker(
                    rememberMarkerState(position = position),
                    title = gasolinera.rotulo,
                    snippet = gasolinera.direccion
                )
            }
            mapViewModel.restartBounds()
        }
    }
}

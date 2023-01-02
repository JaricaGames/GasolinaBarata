package com.jarica.preciogasolina.ui.ui.Map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.jarica.preciogasolina.data.network.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.data.network.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.ui.ui.List.ListViewModel


@Composable
fun MapUi(mapViewModel: MapViewModel, listViewModel: ListViewModel) {

    val gasolineList by listViewModel.gasList.observeAsState(listOf())
    val gasolineListByGasAndTown by listViewModel.gasListByGasAndTown.observeAsState(listOf())
    val mapProperties by mapViewModel.mapProperties.observeAsState(MapProperties(mapType = MapType.NORMAL))
    val cameraPositionState by mapViewModel.cameraPositionState.observeAsState(
        CameraPositionState(
            position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 30f)
        )
    )
    val cameraPositionState2 = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(40.4165000, -3.7025600), 5.3f)
    }



    Box(
        Modifier.fillMaxSize(),
    ) {
        MyGoogleMap(
            gasolineList,
            gasolineListByGasAndTown,
            mapViewModel,
            mapProperties,
            cameraPositionState2
            )
    }

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

        if (!gasolineList.isNullOrEmpty()) {
            mapViewModel.getCoordenatesByTown(gasolineList)
            gasolineList.forEach { gasolinera ->

                var longitud = mapViewModel.replaceString(gasolinera.longitud)
                var latitud = mapViewModel.replaceString(gasolinera.latitud)

                val bounds = mapViewModel.getBounds(latitud, longitud)
                val position = LatLng(latitud, longitud)

                cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds,150))
                Marker(rememberMarkerState(position = position), title = gasolinera.rotulo)

            }
        }
        if (!gasolineListByGasAndTown.isNullOrEmpty()) {
            mapViewModel.getCoordenatesByGasAndTown(gasolineListByGasAndTown)

            gasolineListByGasAndTown.forEach { gasolinera ->
                var longitud = mapViewModel.replaceString(gasolinera.longitud)
                var latitud = mapViewModel.replaceString(gasolinera.latitud)

                val bounds = mapViewModel.getBounds(latitud, longitud)
                val position = LatLng(latitud, longitud)

                cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds,150))
                Marker(rememberMarkerState(position = position), title = gasolinera.rotulo, snippet = gasolinera.direccion)
            }
        }
    }
}

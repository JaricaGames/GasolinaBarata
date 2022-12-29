package com.jarica.preciogasolina.ui.ui.Map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.GoogleMap


@Composable
fun MapUi(mapViewModel: MapViewModel) {
    Box(
        Modifier.fillMaxSize(),
    ) {
        MyGoogleMap()
    }

}

@Composable
fun MyGoogleMap() {
}

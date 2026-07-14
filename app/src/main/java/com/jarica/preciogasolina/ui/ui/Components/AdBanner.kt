package com.jarica.preciogasolina.ui.ui.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

//BANNER ADMOB COMPARTIDO: CADA PANTALLA PASA SU adUnitId Y TAMAÑO
@Composable
fun AdBanner(
    adUnitId: String,
    adSize: AdSize = AdSize.BANNER,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(adSize)
                this.adUnitId = adUnitId
                loadAd(AdRequest.Builder().build())
            }
        },
        onRelease = { it.destroy() }
    )
}

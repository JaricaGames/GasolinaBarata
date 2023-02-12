package com.jarica.preciogasolina.ui.ui.List.Screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.data.network.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.ui.theme.poppins
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.nameGasolinaSeleccionada

@Composable
fun cardStationByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio) {

    var isSelectedCard = rememberSaveable { mutableStateOf(false) }
    var context = LocalContext.current

    Card(
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .fillMaxWidth(),
        backgroundColor = colorResource(id = R.color.Beige)
    ) {
        Column() {

            Row(Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
                ImageLogoByGasolineAndTown(gasStation)
                GasStationTextByGasolineAndTown(gasStation, isSelectedCard)
            }
            if (isSelectedCard.value) {
                MasInfoCardByGasolineAndTown(gasStation)
                Spacer(modifier = Modifier.size(8.dp))
                GoogleMapCardInfoByGasolineAndTown(
                    Modifier
                        .size(250.dp, 250.dp)
                        .align(Alignment.CenterHorizontally),
                    gasStation
                )
                Spacer(modifier = Modifier.size(8.dp))
                NavegateButtonByGasolineAndTown(Modifier.align(Alignment.CenterHorizontally), gasStation, context )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }


    }


}

@Composable
fun NavegateButtonByGasolineAndTown(
    modifier: Modifier,
    gasStation: GasolineraPorGasolinaYMunicipio,
    context: Context
) {
    Button(
        modifier = modifier,
        onClick = { lanzarMapsByGasolineAndTown(gasStation, context) }) {
        Icon(
            imageVector = Icons.Filled.Place,
            contentDescription = "",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "Navegar",
            fontSize = 14.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal
        )
    }
}

fun lanzarMapsByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio, context: Context) {

    var latitud = gasStation.latitud
    var longitud = gasStation.longitud
    val gmmIntentUri =
        Uri.parse("google.navigation:q=$latitud,$longitud")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(context, mapIntent, bundleOf())
}

fun replaceString(coordenada: String): Double {
    var coordenadaOK = coordenada.replace(",", ".", false)
    return coordenadaOK.toDouble()
}

@Composable
fun GoogleMapCardInfoByGasolineAndTown(modifier: Modifier, gasStation: GasolineraPorGasolinaYMunicipio) {

    val gasStationPosition =
        LatLng(replaceString(gasStation.latitud), replaceString(gasStation.longitud))
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(gasStationPosition, 17f)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    ) {
        Marker(
            state = MarkerState(position = gasStationPosition),
            title = gasStation.rotulo
        )
    }
}

@Composable
fun MasInfoCardByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio) {
    Column(Modifier.padding(horizontal = 8.dp)) {
        Row() {
            Text(
                text = "Poblacion: ",
                fontFamily = poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = gasStation.localidad,
                fontFamily = poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }
        Row() {
            Text(
                text = "Provincia: ",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = gasStation.provincia,
                fontFamily = poppins,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
        }
        Row() {
            Text(
                text = "Horario: ",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = gasStation.horario,
                fontFamily = poppins,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
        }
    }


}


@Composable
fun TextoMasByGasolineAndTown(isSelectedCard: MutableState<Boolean>, modifier: Modifier) {
    Row(
        modifier.clickable { isSelectedCard.value = !isSelectedCard.value },
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.size(25.dp, 25.dp),
            onClick = { isSelectedCard.value = !isSelectedCard.value }) {
            Icon(
                Icons.Filled.ArrowDropDown, "",
                Modifier.rotate(
                    if (isSelectedCard.value) 180f else 360f
                )
            )

        }
        Spacer(modifier = Modifier.size(8.dp))
        if (isSelectedCard.value) Text(
            text = "Menos...",
            fontSize = 12.sp
        ) else Text(text = "Mas...", fontSize = 12.sp)


    }
}

@Composable
fun GasStationTextByGasolineAndTown(
    gasStation: GasolineraPorGasolinaYMunicipio,
    isSelectedCard: MutableState<Boolean>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        GasStationTitleByGasolineAndTown(gasStation)
        GasStationAddressByGasolineAndTown(gasStation)
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.GrisClaro))
        )
        PriceGasByGasolineAndTown(gasStation)
        TextoMasByGasolineAndTown(isSelectedCard, Modifier.align(Alignment.End))

    }
}


@Composable
fun PriceGasByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio) {
    PrecioSeleccionado(gasStation)

}

@Composable
fun PrecioSeleccionado(gasStation: GasolineraPorGasolinaYMunicipio) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$nameGasolinaSeleccionada: ",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = gasStation.precioProducto + " €",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(
                id = R.color.NaranjaClaro
            ), fontFamily = poppins
        )
    }

}


@Composable
fun GasStationAddressByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = gasStation.direccion, fontSize = 12.sp, fontFamily = poppins)
    }
}

@Composable
fun GasStationTitleByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = gasStation.rotulo,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppins
        )
    }
}

@Composable
fun ImageLogoByGasolineAndTown(
    gasStation: GasolineraPorGasolinaYMunicipio
) {

    Box(Modifier.width(60.dp), Alignment.BottomCenter) {
        when (gasStation.rotulo) {
            "CEPSA" -> Image(
                painter = painterResource(id = R.drawable.logocepsa),
                contentDescription = "CEPSA"
            )
            "PLENOIL" -> Image(
                painter = painterResource(id = R.drawable.logoplenoil),
                contentDescription = "PLENOIL"
            )
            "CARREFOUR" -> Image(
                painter = painterResource(id = R.drawable.logocarrefour),
                contentDescription = "CARREFOUR"
            )
            "BP" -> Image(
                painter = painterResource(id = R.drawable.logobp),
                contentDescription = "BP"
            )
            "REPSOL" -> Image(
                painter = painterResource(id = R.drawable.logorepsol),
                contentDescription = "REPSOL"
            )
            "ALCAMPO" -> Image(
                painter = painterResource(id = R.drawable.logoalcampo),
                contentDescription = "ALCAMPO"
            )
            "GALP" -> Image(
                painter = painterResource(id = R.drawable.logogalp),
                contentDescription = "GALP"
            )
            "SHELL" -> Image(
                painter = painterResource(id = R.drawable.logoshell),
                contentDescription = "SHELL"
            )
            "BALLENOIL" -> Image(
                painter = painterResource(id = R.drawable.logoballenoil),
                contentDescription = "BALLENOIL"
            )
            "Q8" -> Image(
                painter = painterResource(id = R.drawable.logoq8),
                contentDescription = "Q8"
            )
            "PETROGOLD" -> Image(
                painter = painterResource(id = R.drawable.logopetrogold),
                contentDescription = "PETROGOLD"
            )
            "SETTRAN" -> Image(
                painter = painterResource(id = R.drawable.logosettran),
                contentDescription = "SETTRAN"
            )
            "NATURGY" -> Image(
                painter = painterResource(id = R.drawable.logonaturgy),
                contentDescription = "NATURGY"
            )
            "CAMPSA" -> Image(
                painter = painterResource(id = R.drawable.logocampsa),
                contentDescription = "CAMPSA"
            )
            "SUPECO" -> Image(
                painter = painterResource(id = R.drawable.logosupeco),
                contentDescription = "SUPECO"
            )
            "SARAS" -> Image(
                painter = painterResource(id = R.drawable.logosaras),
                contentDescription = "SARAS"
            )
            else -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_gasstationdefault),
                    contentDescription = "default"
                )
            }
        }
    }
}
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.ui.theme.poppins
import com.jarica.preciogasolina.ui.ui.List.ListViewModel


@Composable
fun cardStationByTowns(
    gasStation: GasolineraPorMunicipio,
    listViewModel: ListViewModel,
    listFavId: MutableList<String>
) {


    var isSelectedCard = rememberSaveable { mutableStateOf(false) }

    var context = LocalContext.current

    Card(
        elevation = 10.dp,
        modifier =
        Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .fillMaxWidth(), backgroundColor = colorResource(id = R.color.Beige)
    ) {
        Column() {
            Row(
                Modifier
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageLogoByTown(gasStation, Modifier)
                GasStationTextByTown(listViewModel, gasStation, isSelectedCard, listFavId)

            }
            if (isSelectedCard.value) {
                MasInfoCard(gasStation)
                Spacer(modifier = Modifier.size(8.dp))
                GoogleMapCardInfoByTown(
                    Modifier
                        .size(250.dp, 250.dp)
                        .align(Alignment.CenterHorizontally),
                    gasStation
                )
                Spacer(modifier = Modifier.size(8.dp))
                NavegateButtonByTown(
                    Modifier.align(Alignment.CenterHorizontally),
                    gasStation,
                    context
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }


}

@Composable
fun GoogleMapCardInfoByTown(modifier: Modifier, gasStation: GasolineraPorMunicipio) {

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
fun NavegateButtonByTown(
    modifier: Modifier,
    gasStation: GasolineraPorMunicipio,
    context: Context
) {
    Button(
        modifier = modifier,
        onClick = { lanzarMapsByTown(gasStation, context) }) {
        Icon(
            imageVector = Icons.Filled.Place,
            contentDescription = "",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "Navegar",
            fontSize = 14.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}

fun lanzarMapsByTown(gasStation: GasolineraPorMunicipio, context: Context) {

    var latitud = gasStation.latitud
    var longitud = gasStation.longitud
    val gmmIntentUri =
        Uri.parse("google.navigation:q=$latitud,$longitud")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    ContextCompat.startActivity(context, mapIntent, bundleOf())
}

@Composable
fun MasInfoCard(gasStation: GasolineraPorMunicipio) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row {
            Text(
                text = "Poblacion: ",
                fontFamily = poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = gasStation.localidad,
                fontFamily = poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
        }
        Row {
            Text(
                text = "Provincia: ",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = gasStation.provincia,
                fontFamily = poppins,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
        Row() {
            Text(
                text = "Horario: ",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = gasStation.horario,
                fontFamily = poppins,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }


}


@Composable
fun GasStationTextByTown(
    listViewModel: ListViewModel,
    gasStation: GasolineraPorMunicipio,
    isSelectedCard: MutableState<Boolean>,
    listFavId: MutableList<String>

) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        GasStationTitle(gasStation)
        GasStationAddress(gasStation)
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.GrisClaro))
        )
        Spacer(modifier = Modifier.height(2.dp))
        PriceGas(gasStation)
        TextoMas(isSelectedCard, Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.height(2.dp))
        FavButtonByTown(listViewModel, Modifier.align(Alignment.End), gasStation, listFavId)
    }
}


@Composable
fun FavButtonByTown(
    listViewModel: ListViewModel,
    modifier: Modifier,
    gasStation: GasolineraPorMunicipio,
    listFavId: MutableList<String>
) {


    Row(
        modifier.clickable {
            if (listFavId.contains(gasStation.iDEESS)) {
                listViewModel.deleteFavorite(gasStation.iDEESS)
            } else {
                listViewModel.addFavorite(gasStation.iDEESS)
            }
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (listFavId.contains(gasStation.iDEESS)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_starfilled_24),
                contentDescription = "",
                tint = colorResource(
                    id = R.color.Naranja
                )
            )

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Eliminar favoritos",
                fontSize = 11.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_starempty_24),
                contentDescription = "",
                tint = colorResource(
                    id = R.color.Naranja
                )
            )

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Añadir a favoritos",
                fontSize = 11.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }

}

@Composable
fun TextoMas(isSelectedCard: MutableState<Boolean>, modifier: Modifier) {

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
        if (isSelectedCard.value)
            Text(
                text = "Menos Info...",
                fontSize = 11.sp, fontFamily = poppins, fontWeight = FontWeight.Medium
            ) else
            Text(
                text = "Mas Info...",
                fontSize = 11.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
    }
}


@Composable
fun PriceGas(gasStation: GasolineraPorMunicipio) {

    PrecioGasoil(gasStation)
    PrecioGasoilPremium(gasStation)
    PrecioGasolina95E5(gasStation)
    PrecioGasolina95E10(gasStation)
    PrecioGasolina98E5(gasStation)
    PrecioGasolina98E10(gasStation)
    PrecioGasolina95E5Premium(gasStation)
    PrecioGasoleoB(gasStation)
    PrecioBioetanol(gasStation)
    PrecioBiodiesel(gasStation)
    PrecioGNC(gasStation)
    PrecioGLP(gasStation)


}

@Composable
fun PrecioGLP(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGaseslicuadosdelpetroleo.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioGLP),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoPremium + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGNC(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasNaturalComprimido.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioGNC),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasNaturalComprimido + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioBiodiesel(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioBiodiesel.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioBiodiesel),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoPremium + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioBioetanol(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioBioetanol.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioBioetanol),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoPremium + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGasoleoB(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasoleoB.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioGasoleoB),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoB + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGasolina95E10(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasolina95E10.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioGasolina95E10),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoPremium + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGasolina95E5Premium(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasolina95E5Premium.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioGasolina95E5Premium),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoPremium + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGasolina98E10(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasolina98E5.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioGasolina98E5),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoPremium + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGasolina98E5(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasolina98E10.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioGasolina98E10),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoPremium + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGasolina95E5(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasolina95E5.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.GasolinaSinPlomo),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasolina95E5 + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGasoilPremium(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasoleoPremium.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.precioGasoleoPremium),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoPremium + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun PrecioGasoil(gasStation: GasolineraPorMunicipio) {
    if (!gasStation.precioGasoleoA.isNullOrEmpty()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.Diesel),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = gasStation.precioGasoleoA + " €",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                color = colorResource(
                    id = R.color.NaranjaClaro
                )
            )
        }
    }
}

@Composable
fun GasStationAddress(gasStation: GasolineraPorMunicipio) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            text = gasStation.direccion,
            fontSize = 15.sp,
            color = Color.Black
        )

        Text(
            text = gasStation.localidad,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppins,
            color = Color.Black
        )
    }
}

@Composable
fun GasStationTitle(gasStation: GasolineraPorMunicipio) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = gasStation.rotulo,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun ImageLogoByTown(
    gasStation: GasolineraPorMunicipio,
    Modifier: Modifier
) {

    Box(Modifier.width(60.dp), Alignment.Center) {
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
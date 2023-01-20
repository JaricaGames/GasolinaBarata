package com.jarica.preciogasolina.ui.ui.List.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.data.network.response.GasolineraPorMunicipio
import com.jarica.preciogasolina.ui.theme.poppins
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel

@Composable
fun cardStationByTowns(
    gasStation: GasolineraPorMunicipio
) {

    Card(
        elevation = 8.dp, modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .fillMaxWidth(), backgroundColor = colorResource(id = R.color.Beige)
    ) {
        Row(Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
            ImageLogoByTown(gasStation, Modifier)
            GasStationTextByTown(gasStation)
        }

    }
}

@Composable
fun GasStationTextByTown(
    gasStation: GasolineraPorMunicipio
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        GasStationTitle(gasStation)
        GasStationAddress(gasStation)
        Spacer(modifier = Modifier.height(2.dp))
        Box(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.Gris))) {
            
        }
        PriceGas(gasStation)

    }
}


@Composable
fun PriceGas(gasStation: GasolineraPorMunicipio) {
    if (SearchViewModel.idGasolinaSeleccionada == "") {
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
                fontWeight = FontWeight.Bold
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
            Text(text = stringResource(id = R.string.Diesel), fontWeight = FontWeight.Bold)
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
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = gasStation.direccion, fontSize = 15.sp)
    }
}

@Composable
fun GasStationTitle(gasStation: GasolineraPorMunicipio) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = gasStation.rotulo, fontSize = 24.sp, fontWeight = FontWeight.Bold)
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
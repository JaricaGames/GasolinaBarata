package com.jarica.preciogasolina.ui.ui.List.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.data.network.response.GasolineraPorGasolinaYMunicipio
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.nameGasolinaSeleccionada

@Composable
fun cardStationByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio) {


            Card(
                elevation = 8.dp, modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .fillMaxWidth(), backgroundColor = colorResource(id = R.color.Beige)
            ) {
                Row(Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    ImageLogoByGasolineAndTown(gasStation)
                    GasStationTextByGasolineAndTown(gasStation)
                }

            }


}

@Composable
fun GasStationTextByGasolineAndTown(
    gasStation: GasolineraPorGasolinaYMunicipio
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        GasStationTitleByGasolineAndTown(gasStation)
        GasStationAddressByGasolineAndTown(gasStation)
        PriceGasByGasolineAndTown(gasStation)

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
            text = nameGasolinaSeleccionada,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(3.dp))
        Text(
            text = gasStation.precioProducto + " €",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(
                id = R.color.NaranjaClaro
            )
        )
    }

}


@Composable
fun GasStationAddressByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = gasStation.direccion, fontSize = 15.sp)
    }
}

@Composable
fun GasStationTitleByGasolineAndTown(gasStation: GasolineraPorGasolinaYMunicipio) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = gasStation.rotulo, fontSize = 24.sp, fontWeight = FontWeight.Bold)
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
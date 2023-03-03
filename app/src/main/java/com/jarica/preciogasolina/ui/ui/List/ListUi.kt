package com.jarica.preciogasolina.ui.ui.List

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.ui.List.Screens.cardStationByGasolineAndTown
import com.jarica.preciogasolina.ui.ui.List.Screens.cardStationByTowns
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.idGasolinaSeleccionada


@Composable
fun ListUi(listViewModel: ListViewModel, navController: NavHostController) {

    val gasList by listViewModel.gasList.observeAsState(listOf())
    val gasListByGasAndTown by listViewModel.gasListByGasAndTown.observeAsState(listOf())

    if (idGasolinaSeleccionada == "") {
        if (gasList.isEmpty()) {
            EmptyGasStationList(navController)
        } else {
            Column(Modifier.fillMaxWidth()) {
                LazyColumn(
                    Modifier.padding(top = 6.dp, bottom = 65.dp)) {
                    var contador = 0
                    items(gasList) { gasStation ->
                        contador ++
                        cardStationByTowns(gasStation, listViewModel)
                        if(contador%5 == 0){
                            Spacer(modifier = Modifier.height(10.dp))
                            BannerAdView()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                BannerAdView()
            }


        }
    } else {
        if (gasListByGasAndTown.isEmpty()) {
            EmptyGasStationList(navController)
        } else {

        }
        LazyColumn(Modifier.padding(top = 6.dp, bottom = 65.dp)) {
            var contador = 0
            items(gasListByGasAndTown) { gasStation ->
                contador ++
                cardStationByGasolineAndTown(gasStation, listViewModel)
                if(contador%5 == 0){
                    Spacer(modifier = Modifier.height(10.dp))
                    BannerAdView()
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
        }

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
                setAdSize(AdSize.FULL_BANNER)
                // Add your adUnitID, this is for testing.
                adUnitId = "ca-app-pub-4979320410432560/7752668839"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}



@Composable
fun EmptyGasStationList(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_undrawsearching),
            contentDescription = "default"
        )
        Spacer(modifier = Modifier.size(40.dp))
        Text(
            text = stringResource(id = R.string.result0),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(id = R.string.result0_2),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.size(32.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            onClick = { navController.navigate(Destinations.SearchScreen.route) }) {
            Text(
                text = stringResource(id = R.string.Buttonresult0),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }

}




package com.jarica.preciogasolina.ui.ui.List

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.ui.List.Screens.cardStationByGasolineAndTown
import com.jarica.preciogasolina.ui.ui.List.Screens.cardStationByTowns
import com.jarica.preciogasolina.ui.ui.Navigation.SearchScreen
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel.Companion.idGasolinaSeleccionada


@Composable
fun ListUi(listViewModel: ListViewModel, navController: NavHostController) {

    val gasList by listViewModel.gasList.observeAsState(listOf())
    val gasListByGasAndTown by listViewModel.gasListByGasAndTown.observeAsState(listOf())

    if (idGasolinaSeleccionada == "") {
        if (gasList.isEmpty()) {
            emptyGasStationList(navController)
        } else {
            LazyColumn(Modifier.padding(top = 6.dp, bottom = 65.dp)) {
                items(gasList) { gasStation ->
                    cardStationByTowns(gasStation)
                }
            }
        }
    } else {
        if (gasListByGasAndTown.isEmpty()) {
            emptyGasStationList(navController)
        } else {

        }
        LazyColumn(Modifier.padding(top = 6.dp, bottom = 65.dp)) {
            items(gasListByGasAndTown) { gasStation ->
                cardStationByGasolineAndTown(gasStation)

            }
        }

    }

}


@Composable
fun emptyGasStationList(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_undrawsearching),
            contentDescription = "default"
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            text = stringResource(id = R.string.result0),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(id = R.string.result0_2),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(32.dp))
        Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = { navController.navigate(SearchScreen.route) }) {
            Text(
                text = stringResource(id = R.string.Buttonresult0),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }

}



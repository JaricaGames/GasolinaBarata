package com.jarica.preciogasolina.ui.ui.Search


import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.data.network.response.Gasolina
import com.jarica.preciogasolina.data.network.response.Province
import com.jarica.preciogasolina.data.network.response.Towns
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.ListScreen


@Composable
fun SearchUi(
    searchViewModel: SearchViewModel,
    navController: NavHostController,
    listViewModel: ListViewModel
) {

    val gasoline: String by searchViewModel.gasolineSelected.observeAsState(initial = "")
    val isGasolineExpanded: Boolean by searchViewModel.isGasolineExpanded.observeAsState(initial = false)
    val gasolineList by searchViewModel.gasolineList.observeAsState(listOf())

    val province: String by searchViewModel.provinceSelected.observeAsState(initial = "")
    val isProvinceExpanded: Boolean by searchViewModel.isProvinceExpanded.observeAsState(initial = false)
    val ProvinceList by searchViewModel.provincesList.observeAsState(listOf())
    val isProviceSelected: Boolean by searchViewModel.isProvinceSelected.observeAsState(initial = false)

    val town: String by searchViewModel.townSelected.observeAsState(initial = "")
    val isTownExpanded: Boolean by searchViewModel.isTownExpanded.observeAsState(initial = false)
    val townsList by searchViewModel.townsList.observeAsState(listOf())
    val isTownSelected: Boolean by searchViewModel.isTownSelected.observeAsState(initial = false)


    val isDataCharging: Boolean by searchViewModel.isDataCharging.observeAsState(initial = false)


    if (!isDataCharging) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            EDTSeleccioneGasStation(
                gasoline,
                isGasolineExpanded,
                gasolineList,
                searchViewModel
            )
            if (!isGasolineExpanded) {
                Spacer(modifier = Modifier.size(16.dp))
                EDTSeleccioneProvincia(province, isProvinceExpanded, ProvinceList, searchViewModel)

                if (isProviceSelected && !isProvinceExpanded) {
                    Spacer(modifier = Modifier.size(16.dp))
                    EDTSeleccioneMunicipio(
                        town,
                        isTownExpanded,
                        townsList,
                        isTownSelected,
                        searchViewModel,
                    )
                }


                if (isTownSelected) {
                    searchButton(navController, listViewModel, gasoline)
                }
            }

            AndroidView(modifier = Modifier.fillMaxWidth(), factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.FULL_BANNER)
                    adUnitId = "ca-app-pub-3940256099942544~3347511713"
                    loadAd(AdRequest.Builder().build())
                }

            } )

        }
    } else {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }


}


@Composable
fun searchButton(
    navController: NavHostController,
    listViewModel: ListViewModel,
    gasoline: String,
) {
    Spacer(modifier = Modifier.size(25.dp))
    Button(
        onClick = {
            if (gasoline.isEmpty()) {
                navController.navigate(ListScreen.route)
                listViewModel.getGasStationsByTowns()
            } else {
                navController.navigate(ListScreen.route)
                listViewModel.getGasStationsByTownsAndGasoline()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = stringResource(id = R.string.searchButtton))
    }
}


@Composable
fun EDTSeleccioneMunicipio(
    town: String,
    isTownExpanded: Boolean,
    townsList: List<Towns>,
    isTownSelected: Boolean,
    searchViewModel: SearchViewModel
) {
    OutlinedTextField(
        value = town,
        onValueChange = { },
        modifier = Modifier
            .clickable {
                searchViewModel.onTownClicked(isTownExpanded)
            }
            .fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.seleccioneMunicipio)) },
        enabled = false,
        readOnly = true,
        trailingIcon = {
            if (isTownExpanded) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = ""
                )
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = ""
                )
            }

        },
    )
    DropdownMenu(

        expanded = isTownExpanded,
        onDismissRequest = {
            searchViewModel.onTownClicked(
                isTownExpanded,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(500.dp)

    ) {
        townsList.forEach { label ->
            DropdownMenuItem(onClick = {
                searchViewModel.onTownSelected(label.Municipio, isTownSelected, label.IDMunicipio)
                searchViewModel.onTownClicked(isTownExpanded)

            }) {
                Text(text = label.Municipio)
            }
        }
    }
}

@Composable
fun EDTSeleccioneProvincia(
    province: String,
    isProvinceExpanded: Boolean,
    ProvinceList: List<Province>,
    searchViewModel: SearchViewModel
) {
    OutlinedTextField(
        value = province,
        onValueChange = { searchViewModel.onProvinceSelectedChanged(province) },
        modifier = Modifier
            .clickable {
                searchViewModel.isProvinceSelected(isProvinceExpanded)
            }
            .fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.seleccioneProvincia)) },
        enabled = false,
        readOnly = true,
        trailingIcon = {
            if (isProvinceExpanded) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = ""
                )
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = ""
                )
            }

        },
    )

    DropdownMenu(
        expanded = isProvinceExpanded,
        onDismissRequest = {
            searchViewModel.isProvinceSelected(isProvinceExpanded)

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(500.dp)

    ) {
        ProvinceList.forEach { label ->
            DropdownMenuItem(onClick = {
                searchViewModel.onProvinceSelected(label.Provincia, label.IDProvincia)
                searchViewModel.isProvinceSelected(isProvinceExpanded)
            }) {
                Text(text = label.Provincia, fontSize = 15.sp)
            }


        }
    }
}


@Composable
fun EDTSeleccioneGasStation(
    gasoline: String,
    isGasolineExpanded: Boolean,
    gasolineList: List<Gasolina>,
    searchViewModel: SearchViewModel
) {
    OutlinedTextField(
        value = gasoline,
        onValueChange = { },
        modifier = Modifier
            .clickable {
                searchViewModel.isGasolineSelected(isGasolineExpanded)
            }
            .fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.seleccioneCarburante)) },
        enabled = false,
        readOnly = true,
        trailingIcon = {
            if (isGasolineExpanded) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = ""
                )
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = ""
                )
            }

        },
    )

    DropdownMenu(
        expanded = isGasolineExpanded,
        onDismissRequest = {
            searchViewModel.onGasolineClicked(isGasolineExpanded)
            searchViewModel.onDismissGasoline()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(500.dp)

    ) {
        gasolineList.forEach { label ->
            DropdownMenuItem(onClick = {
                searchViewModel.onGasolineSelected(label.iDProducto, label.nombreProducto)
                searchViewModel.isGasolineSelected(isGasolineExpanded)
            }) {
                Text(text = label.nombreProducto, fontSize = 15.sp)
            }


        }
    }
}






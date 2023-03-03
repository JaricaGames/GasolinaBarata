package com.jarica.preciogasolina.ui.ui.Search


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.data.network.Retrofit.response.Gasolina
import com.jarica.preciogasolina.data.network.Retrofit.response.Province
import com.jarica.preciogasolina.data.network.Retrofit.response.Towns
import com.jarica.preciogasolina.ui.theme.poppins
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations


@Composable
fun SearchUi(
    searchViewModel: SearchViewModel,
    navController: NavHostController,
    listViewModel: ListViewModel,
) {

    val gasoline: String by searchViewModel.gasolineSelected.observeAsState(initial = "")
    val isGasolineExpanded: Boolean by searchViewModel.isGasolineExpanded.observeAsState(initial = false)
    val gasolineList by searchViewModel.gasolineList.observeAsState(listOf())

    val province: String by searchViewModel.provinceSelected.observeAsState(initial = "")
    val isProvinceExpanded: Boolean by searchViewModel.isProvinceExpanded.observeAsState(initial = false)
    val provinceList by searchViewModel.provincesList.observeAsState(listOf())
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
                .background(colorResource(id = R.color.Beige)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Texto(stringResource(id = R.string.seleccioneCarburante))
            Spacer(modifier = Modifier.size(8.dp))

            EDTSeleccioneGasStation(
                gasoline,
                isGasolineExpanded,
                gasolineList,
                searchViewModel
            )
            if (!isGasolineExpanded) {
                Spacer(modifier = Modifier.size(8.dp))
                Texto(stringResource(R.string.seleccioneProvincia))
                Spacer(modifier = Modifier.size(8.dp))
                EDTSeleccioneProvincia(province, isProvinceExpanded, provinceList, searchViewModel)


                if (isProviceSelected && !isProvinceExpanded) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Texto(stringResource(R.string.seleccioneMunicipio))
                    Spacer(modifier = Modifier.size(8.dp))
                    EDTSeleccioneMunicipio(
                        town,
                        isTownExpanded,
                        townsList,
                        isTownSelected,
                        searchViewModel,
                    )
                }


                if (!isTownExpanded && !isProvinceExpanded) {
                    Spacer(modifier = Modifier.size(16.dp))
                    BannerAdView()
                    Spacer(modifier = Modifier.size(16.dp))
                    SearchButton(navController, listViewModel, gasoline, isTownSelected)
                }

            }

        }
    } else {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(id = R.color.Naranja)
                )
        ) {
            CircularProgressIndicator()
        }
    }


}


@Composable
fun Texto(Texto: String) {

    Text(
        text = Texto,
        fontSize = 16.sp,
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        color = colorResource(id = R.color.Gris),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun EDTSeleccioneGasStation(
    gasoline: String,
    isGasolineExpanded: Boolean,
    gasolineList: List<Gasolina>,
    searchViewModel: SearchViewModel
) {
    TextField(
        value = gasoline,
        textStyle = TextStyle(fontFamily = poppins, fontSize = 14.sp),
        onValueChange = { },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = colorResource(id = R.color.GrisClaro),
            disabledBorderColor = Color.Transparent
        ),
        enabled = false,
        readOnly = true,
        shape = RoundedCornerShape(10.dp),

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
        modifier = Modifier
            .clickable {
                searchViewModel.isGasolineSelected(isGasolineExpanded)
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )

    DropdownMenu(
        expanded = isGasolineExpanded,
        onDismissRequest = {
            searchViewModel.onGasolineClicked(isGasolineExpanded)
            searchViewModel.onDismissGasoline()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)

    ) {
        gasolineList.forEach { label ->
            DropdownMenuItem(modifier = Modifier.height(25.dp),
                onClick = {
                    searchViewModel.onGasolineSelected(label.iDProducto, label.nombreProducto)
                    searchViewModel.isGasolineSelected(isGasolineExpanded)
                }) {
                Text(
                    text = label.nombreProducto,
                    fontSize = 14.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
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
                searchViewModel.onDismissProvince()
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        enabled = false,
        readOnly = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = colorResource(id = R.color.GrisClaro),
            disabledBorderColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
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
            DropdownMenuItem(modifier = Modifier.height(25.dp),
                onClick = {
                    searchViewModel.onProvinceSelected(label.Provincia, label.IDProvincia)
                    searchViewModel.isProvinceSelected(isProvinceExpanded)
                }) {
                Text(
                    text = label.Provincia,
                    fontSize = 14.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
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
                setAdSize(AdSize.MEDIUM_RECTANGLE)
                adUnitId = "ca-app-pub-4979320410432560/7752668839"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}


@Composable
fun SearchButton(
    navController: NavHostController,
    listViewModel: ListViewModel,
    gasoline: String,
    isTownSelected: Boolean
) {
    Button(
        onClick = {
            if (gasoline.isEmpty()) {
                navController.navigate(Destinations.ListScreen.route)
                listViewModel.getGasStationsByTowns()
            } else {
                navController.navigate(Destinations.ListScreen.route)
                listViewModel.getGasStationsByTownsAndGasoline()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp)
            .height(40.dp),
        enabled = isTownSelected,
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(text = stringResource(id = R.string.searchButtton), fontFamily = poppins, color = Color.White)
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
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        enabled = false,
        readOnly = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = colorResource(id = R.color.GrisClaro),
            disabledBorderColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
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
            searchViewModel.onTownClicked(isTownExpanded)
            searchViewModel.onDismissTown()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .height(500.dp)

    ) {
        townsList.forEach { label ->
            DropdownMenuItem(modifier = Modifier.height(25.dp),
                onClick = {
                    searchViewModel.onTownSelected(
                        label.Municipio,
                        isTownSelected,
                        label.IDMunicipio
                    )
                    searchViewModel.onTownClicked(isTownExpanded)

                }) {
                Text(
                    text = label.Municipio,
                    fontSize = 14.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}











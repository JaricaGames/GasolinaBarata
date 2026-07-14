package com.jarica.preciogasolina.ui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jarica.preciogasolina.ui.theme.Fondo
import com.jarica.preciogasolina.ui.ui.Components.BottomNavigationBar
import com.jarica.preciogasolina.ui.ui.Detail.StationDetailUi
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.Map.MapViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.*
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel


@Composable
fun MainScreenUi(
    searchViewModel: SearchViewModel,
    mapViewModel: MapViewModel,
    listViewModel: ListViewModel,
    favViewModel: FavViewModel
) {

    val navController2 = rememberNavController()

    val navigationItems = listOf(
        Destinations.SearchScreen,
        Destinations.MapScreen,
        Destinations.ListScreen,
        Destinations.FavScreen
    )

    //ID DE LA ESTACION ABIERTA EN DETALLE (OVERLAY A PANTALLA COMPLETA)
    val selectedStationId by listViewModel.selectedStationId.observeAsState(null)

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Fondo,
            bottomBar = {
                BottomNavigationBar(navController = navController2, items = navigationItems)
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                HomeNavigationHost(
                    navController = navController2,
                    searchViewModel,
                    mapViewModel,
                    listViewModel,
                    favViewModel
                )
            }
        }

        selectedStationId?.let { id ->
            StationDetailUi(
                stationId = id,
                listViewModel = listViewModel,
                favViewModel = favViewModel
            )
        }
    }
}

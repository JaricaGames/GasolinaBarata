package com.jarica.preciogasolina.ui.ui


import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jarica.preciogasolina.ui.ui.Components.BottomNavigationBar
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.Map.MapViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.*
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController2, items = navigationItems)
        }
    ) {
        HomeNavigationHost(
            navController = navController2,
            searchViewModel,
            mapViewModel,
            listViewModel,
            favViewModel
        )
    }


}

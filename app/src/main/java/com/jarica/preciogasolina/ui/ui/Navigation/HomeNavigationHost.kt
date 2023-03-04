package com.jarica.preciogasolina.ui.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jarica.preciogasolina.ui.ui.FavScreen.FavUi
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.List.ListUi
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.Map.MapUi
import com.jarica.preciogasolina.ui.ui.Map.MapViewModel
import com.jarica.preciogasolina.ui.ui.Search.SearchUi
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel


//COMPOSABLE QUE MANEJA LA NAVEGACION ENTRE LAS PANTALLAS DE LA HOME
@Composable
fun HomeNavigationHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    mapViewModel: MapViewModel,
    listViewModel: ListViewModel,
    favViewModel: FavViewModel
){
    NavHost(navController = navController, startDestination = Destinations.SearchScreen.route){
        composable(Destinations.SearchScreen.route){
            SearchUi(searchViewModel, navController, listViewModel)
        }
        composable(Destinations.MapScreen.route){
            MapUi(mapViewModel, listViewModel)
        }
        composable(Destinations.ListScreen.route){
            ListUi(listViewModel, navController, favViewModel)
        }
        composable(Destinations.FavScreen.route){
            FavUi(favViewModel, listViewModel)
        }
    }
}



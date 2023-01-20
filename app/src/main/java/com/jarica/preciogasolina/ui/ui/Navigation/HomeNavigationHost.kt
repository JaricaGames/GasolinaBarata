package com.jarica.preciogasolina.ui.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    listViewModel: ListViewModel
){
    NavHost(navController = navController, startDestination = SearchScreen.route){
        composable(SearchScreen.route){
            SearchUi(searchViewModel, navController, listViewModel)
        }
        composable(MapScreen.route){
            MapUi(mapViewModel, listViewModel)
        }
        composable(ListScreen.route){
            ListUi(listViewModel, navController)
        }
    }
}
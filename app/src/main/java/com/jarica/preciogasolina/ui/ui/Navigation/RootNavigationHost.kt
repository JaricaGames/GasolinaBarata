package com.jarica.preciogasolina.ui.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.MainScreenUi
import com.jarica.preciogasolina.ui.ui.Map.MapViewModel
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel
import com.jarica.preciogasolina.ui.ui.SplashScreen.SplashScreenUi


//COMPOSABLE QUE MANEJA LA NAVEGACION ENTRE LA SPLASH SCREEN Y LA MAINUI
@Composable
fun RootNavigationHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    mapViewModel: MapViewModel,
    listViewModel: ListViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.SplashScreen.route
    ) {

        composable(Destinations.SplashScreen.route) {
            SplashScreenUi(navController)
        }
        composable(Destinations.MainScreenUi.route) {
            MainScreenUi(searchViewModel, mapViewModel, listViewModel)
        }
    }
}
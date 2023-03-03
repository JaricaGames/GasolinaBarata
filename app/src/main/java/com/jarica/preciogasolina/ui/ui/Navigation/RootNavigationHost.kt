package com.jarica.preciogasolina.ui.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.MainScreenUi
import com.jarica.preciogasolina.ui.ui.Map.MapViewModel
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel
import com.jarica.preciogasolina.ui.ui.SplashScreen.SplashScreenUi
import com.jarica.preciogasolina.ui.ui.SplashScreen.SplashScreenViewModel


//COMPOSABLE QUE MANEJA LA NAVEGACION ENTRE LA SPLASH SCREEN Y LA MAINUI
@Composable
fun RootNavigationHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    mapViewModel: MapViewModel,
    listViewModel: ListViewModel,
    splashScreenViewModel: SplashScreenViewModel,
    favViewModel: FavViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.SplashScreen.route
    ) {

        composable(Destinations.SplashScreen.route) {
            SplashScreenUi(navController, splashScreenViewModel)
        }
        composable(Destinations.MainScreen.route) {
            MainScreenUi(searchViewModel, mapViewModel, listViewModel, favViewModel)
        }
    }
}
package com.jarica.preciogasolina.ui.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.MainScreenUi
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel
import com.jarica.preciogasolina.ui.ui.SplashScreen.SplashScreenUi


//COMPOSABLE QUE MANEJA LA NAVEGACION ENTRE LA SPLASH SCREEN Y LA MAINUI
@Composable
fun RootNavigationHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    listViewModel: ListViewModel,
    favViewModel: FavViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.SplashScreen.route
    ) {

        composable(Destinations.SplashScreen.route) {
            SplashScreenUi(navController, searchViewModel)
        }
        composable(Destinations.MainScreen.route) {
            MainScreenUi(searchViewModel, listViewModel, favViewModel)
        }
    }
}

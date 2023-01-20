package com.jarica.preciogasolina.ui.ui.Navigation

import com.jarica.preciogasolina.R

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: Int
)

object MapScreen : Destinations(
    "MapScreen",
    "Mapa",
    R.drawable.ic_map
)

object ListScreen : Destinations(
    "ListScreen",
    "Lista",
    R.drawable.ic_list
)

object SearchScreen : Destinations(
    "SearchScreen",
    "Buscar",
    R.drawable.ic_search
)

object SplashScreen : Destinations(
    "SplashScreen",
    "SplashScreen",
    R.drawable.ic_search
)

object MainScreenUi : Destinations(
    "MainScreen",
    "MainScreen",
    R.drawable.ic_search
)



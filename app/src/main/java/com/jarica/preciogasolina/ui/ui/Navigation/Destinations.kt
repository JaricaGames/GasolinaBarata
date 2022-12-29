package com.jarica.preciogasolina.ui.ui.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
)

object MapScreen : Destinations(
    "MapScreen",
    "Mapa",
    Icons.Filled.Home
)

object ListScreen : Destinations(
    "ListScreen",
    "Lista",
    Icons.Filled.List
)

object SearchScreen : Destinations(
    "SearchScreen",
    "Buscar",
    Icons.Filled.Search
)



package com.jarica.preciogasolina.ui.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jarica.preciogasolina.ui.theme.Linea
import com.jarica.preciogasolina.ui.theme.Muted3
import com.jarica.preciogasolina.ui.theme.Naranja
import com.jarica.preciogasolina.ui.theme.Sora
import com.jarica.preciogasolina.ui.theme.Superficie
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations

//TAB BAR DEL REDISEÑO: FONDO BLANCO, BORDE SUPERIOR, ACTIVO NARANJA / INACTIVO MUTED
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Destinations>
) {
    val currentRoute = currentRoute(navController = navController)

    Column(
        Modifier
            .fillMaxWidth()
            .background(Superficie)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(thickness = 1.dp, color = Linea)
        Row(Modifier.height(58.dp)) {
            items.forEach { screen ->
                val activa = currentRoute == screen.route
                val color = if (activa) Naranja else Muted3
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.size(5.dp))
                    Text(
                        text = screen.title,
                        fontFamily = Sora,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = color
                    )
                }
            }
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

package com.jarica.preciogasolina.ui.ui.SplashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.poppins
import com.jarica.preciogasolina.ui.ui.MainScreenUi
import com.jarica.preciogasolina.ui.ui.Navigation.MainScreenUi
import com.jarica.preciogasolina.ui.ui.Navigation.SearchScreen
import kotlinx.coroutines.delay


@Composable
fun SplashScreenUi(
    navController: NavHostController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.Naranja)),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "PRECIO DE LA GASOLINA",
            fontFamily = poppins,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
        )

    }

    LaunchedEffect(key1 = true) {
        delay(4000)
        navController.popBackStack()
        navController.navigate(MainScreenUi.route)
    }


}
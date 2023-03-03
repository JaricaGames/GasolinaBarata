package com.jarica.preciogasolina.ui.ui.SplashScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.poppins
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations
import kotlinx.coroutines.*

const val SPLASHSCREEN_DURATION = 4000L

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreenUi(
    navController: NavHostController,
    splashScreenViewModel: SplashScreenViewModel
) {
    val progressIndicator: Float by splashScreenViewModel.progressIndicator.observeAsState(initial = 0f)
    splashScreenViewModel.progressIndicator()



    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.Naranja))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "GASOLINA BARATA",
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = colorResource(id = R.color.white),

        )

        Spacer(modifier = Modifier.size(24.dp))

        Image(
            painter = painterResource(id = R.drawable.sin_t_tulo_1),
            contentDescription = "",
            modifier = Modifier.padding(horizontal = 100.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))


        Text(
            text = "CARGANDO DATOS",
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = colorResource(id = R.color.white)
        )

        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = (progressIndicator * 100).toInt().toString() + " %",
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = colorResource(id = R.color.white)
        )
        LinearProgressIndicator(
            progress = progressIndicator, color = colorResource(id = R.color.white)
        )

    }

    LaunchedEffect(key1 = true) {
        delay(SPLASHSCREEN_DURATION)
        navController.popBackStack()
        navController.navigate(Destinations.MainScreen.route)
    }
}


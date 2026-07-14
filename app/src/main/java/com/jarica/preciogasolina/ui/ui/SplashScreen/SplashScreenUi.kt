package com.jarica.preciogasolina.ui.ui.SplashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.Fondo
import com.jarica.preciogasolina.ui.theme.Ink
import com.jarica.preciogasolina.ui.theme.Linea
import com.jarica.preciogasolina.ui.theme.Muted
import com.jarica.preciogasolina.ui.theme.Naranja
import com.jarica.preciogasolina.ui.theme.NaranjaSuave
import com.jarica.preciogasolina.ui.theme.Sora
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations
import kotlinx.coroutines.delay

const val SPLASHSCREEN_DURATION = 4000L


@Composable
fun SplashScreenUi(
    navController: NavHostController,
    splashScreenViewModel: SplashScreenViewModel
) {
    val progressIndicator: Float by splashScreenViewModel.progressIndicator.observeAsState(initial = 0f)
    splashScreenViewModel.progressIndicator()

    Column(
        modifier = Modifier
            .background(Fondo)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(NaranjaSuave),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_fuel),
                contentDescription = null,
                tint = Naranja,
                modifier = Modifier.size(42.dp)
            )
        }

        Spacer(modifier = Modifier.size(24.dp))

        Text(
            text = "REPOSTAR INTELIGENTE",
            fontFamily = Sora,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 0.14.em,
            color = Naranja
        )

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            text = "Gasolina barata",
            fontFamily = Sora,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 27.sp,
            color = Ink
        )

        Spacer(modifier = Modifier.size(28.dp))

        Text(
            text = "Cargando datos · ${(progressIndicator * 100).toInt()} %",
            fontFamily = Sora,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = Muted
        )

        Spacer(modifier = Modifier.size(12.dp))

        LinearProgressIndicator(
            progress = { progressIndicator },
            color = Naranja,
            trackColor = Linea,
            modifier = Modifier.width(180.dp)
        )
    }

    LaunchedEffect(key1 = true) {
        delay(SPLASHSCREEN_DURATION)
        navController.popBackStack()
        navController.navigate(Destinations.MainScreen.route)
    }
}

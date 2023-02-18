package com.jarica.preciogasolina.ui.ui.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.poppins
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations
import kotlinx.coroutines.delay

const val SPLASHSCREEN_DURATION = 3000L

@Composable
fun SplashScreenUi(
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.Naranja))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "GASOLINERAS DE ESPAÑA",
            fontFamily = poppins,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            color = colorResource(id = R.color.white)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Image(
            painter = painterResource(id = R.drawable.sin_t_tulo_1),
            contentDescription = "",
            modifier = Modifier.padding(horizontal = 100.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))


        Text(
            text = "CARGANDO DATOS",
            fontFamily = poppins,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = colorResource(id = R.color.white)
        )
        Box(modifier = Modifier.size(100.dp)) {
            LottieAnimation()
        }


    }

    LaunchedEffect(key1 = true) {
        delay(SPLASHSCREEN_DURATION)
        navController.popBackStack()
        navController.navigate(Destinations.MainScreenUi.route)
    }
}

@Composable
fun LottieAnimation() {
    val compositeResult: LottieCompositionResult =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    val progressAnimation by animateLottieCompositionAsState(
        compositeResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    LottieAnimation(composition = compositeResult.value, progress = progressAnimation)
}
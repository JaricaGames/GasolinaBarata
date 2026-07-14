package com.jarica.preciogasolina

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.jarica.preciogasolina.ui.theme.PrecioGasolinaTheme
import com.jarica.preciogasolina.ui.ui.FavScreen.FavViewModel
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.Map.MapViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.RootNavigationHost
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel
import com.jarica.preciogasolina.ui.ui.SplashScreen.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()
    private val listViewModel: ListViewModel by viewModels()
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()
    private val favViewModel: FavViewModel by viewModels()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {

        MobileAds.initialize(this)

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            PrecioGasolinaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    //INICIAMOS EL CONTROLADOR DE LA RAIZ QUE INICIA LA SPLASHSCREEN Y TRAS 4 SEGUNDOS INICIA LA MAINSCREEN
                    val navController = rememberNavController()
                    RootNavigationHost(
                        navController = navController,
                        searchViewModel = searchViewModel,
                        mapViewModel = mapViewModel,
                        listViewModel = listViewModel,
                        splashScreenViewModel = splashScreenViewModel,
                        favViewModel = favViewModel
                    )
                }
            }
        }
    }
}

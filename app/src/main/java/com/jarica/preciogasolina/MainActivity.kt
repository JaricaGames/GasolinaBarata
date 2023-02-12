package com.jarica.preciogasolina

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.jarica.preciogasolina.ui.theme.PrecioGasolinaTheme
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.MainScreenUi
import com.jarica.preciogasolina.ui.ui.Map.MapViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.RootNavigationHost
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val searchViewModel:SearchViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()
    private val listViewModel:ListViewModel by viewModels()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {

        MobileAds.initialize(this)

        super.onCreate(savedInstanceState)
        setContent {
            PrecioGasolinaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    //INICIAMOS EL CONTROLADOR DE LA RAIZ QUE INICIA LA SPLASHSCREEN Y TRAS 4 SEGUNDOS INICIA LA MAINSCREEN
                    var navController = rememberNavController()
                    RootNavigationHost(
                        navController = navController,
                        searchViewModel = searchViewModel,
                        mapViewModel = mapViewModel,
                        listViewModel = listViewModel,
                    )
                }
            }
        }
    }
}

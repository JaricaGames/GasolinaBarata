package com.jarica.preciogasolina

import android.annotation.SuppressLint
import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
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
import com.jarica.preciogasolina.ui.ui.Navigation.RootNavigationHost
import com.jarica.preciogasolina.ui.ui.Search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val listViewModel: ListViewModel by viewModels()
    private val favViewModel: FavViewModel by viewModels()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {

        MobileAds.initialize(this)

        //LA APP ES SIEMPRE CLARA: ICONOS OSCUROS EN LAS BARRAS AUNQUE EL SISTEMA ESTE EN MODO OSCURO
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        setContent {
            PrecioGasolinaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    //INICIAMOS EL CONTROLADOR DE LA RAIZ QUE MUESTRA LA SPLASHSCREEN HASTA QUE LOS DATOS ESTAN LISTOS
                    val navController = rememberNavController()
                    RootNavigationHost(
                        navController = navController,
                        searchViewModel = searchViewModel,
                        listViewModel = listViewModel,
                        favViewModel = favViewModel
                    )
                }
            }
        }
    }
}

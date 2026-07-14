package com.jarica.preciogasolina.ui.ui.Search


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.ads.AdSize
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.core.RecentSearch
import com.jarica.preciogasolina.ui.theme.*
import com.jarica.preciogasolina.ui.ui.Components.AdBanner
import com.jarica.preciogasolina.ui.ui.Components.SelectorSheet
import com.jarica.preciogasolina.ui.ui.List.ListViewModel
import com.jarica.preciogasolina.ui.ui.Navigation.Destinations
import com.jarica.preciogasolina.ui.ui.model.formatDelta
import com.jarica.preciogasolina.ui.ui.model.parsePrecio
import java.util.Locale

private const val LITROS_DEPOSITO = 50

private enum class Picker { CARBURANTE, PROVINCIA, MUNICIPIO }

@Composable
fun SearchUi(
    searchViewModel: SearchViewModel,
    navController: NavHostController,
    listViewModel: ListViewModel,
) {

    val gasoline: String by searchViewModel.gasolineSelected.observeAsState(initial = "")
    val gasolineList by searchViewModel.gasolineList.observeAsState(listOf())

    val province: String by searchViewModel.provinceSelected.observeAsState(initial = "")
    val provinceList by searchViewModel.provincesList.observeAsState(listOf())

    val town: String by searchViewModel.townSelected.observeAsState(initial = "")
    val townsList by searchViewModel.townsList.observeAsState(listOf())
    val isTownSelected: Boolean by searchViewModel.isTownSelected.observeAsState(initial = false)

    val isDataCharging: Boolean by searchViewModel.isDataCharging.observeAsState(initial = false)
    val recentSearches by searchViewModel.recentSearches.observeAsState(listOf())

    //RESULTADOS DE LA ULTIMA BUSQUEDA, PARA CALCULAR EL AHORRO REAL DEL HERO
    val gasList by listViewModel.gasList.observeAsState(listOf())
    val gasListByGasAndTown by listViewModel.gasListByGasAndTown.observeAsState(listOf())

    var picker by rememberSaveable { mutableStateOf<Picker?>(null) }

    if (isDataCharging) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Fondo)
        ) {
            CircularProgressIndicator(color = Naranja)
        }
        return
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Fondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
    ) {
        Spacer(Modifier.height(18.dp))

        Text(
            text = "REPOSTAR INTELIGENTE",
            fontFamily = Sora,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = 0.14.em,
            color = Naranja
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Encuentra el mejor precio",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Compara todas las gasolineras de tu municipio y reposta donde más ahorras.",
            fontFamily = Sora,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = Muted,
            modifier = Modifier.widthIn(max = 300.dp)
        )

        Spacer(Modifier.height(16.dp))

        HeroAhorro(
            ahorro = calcularAhorro(gasoline, gasList, gasListByGasAndTown),
            municipio = listViewModel.selectedTownName
        )

        Spacer(Modifier.height(16.dp))

        SelectorCard(
            icono = R.drawable.ic_fuel,
            etiqueta = "CARBURANTE",
            valor = gasoline.ifEmpty { "Todos los carburantes" },
            onClick = { picker = Picker.CARBURANTE }
        )
        Spacer(Modifier.height(10.dp))
        SelectorCard(
            icono = R.drawable.ic_map,
            etiqueta = "PROVINCIA",
            valor = province.ifEmpty { "Selecciona provincia" },
            resaltado = province.isEmpty(),
            onClick = { picker = Picker.PROVINCIA }
        )
        Spacer(Modifier.height(10.dp))
        SelectorCard(
            icono = R.drawable.ic_marker,
            etiqueta = "MUNICIPIO",
            valor = when {
                town.isNotEmpty() -> town
                province.isEmpty() -> "Elige antes una provincia"
                else -> "Selecciona municipio"
            },
            resaltado = town.isEmpty(),
            onClick = { if (province.isNotEmpty()) picker = Picker.MUNICIPIO }
        )

        Spacer(Modifier.height(16.dp))

        SearchButton(
            habilitado = town.isNotEmpty(),
            onClick = {
                searchViewModel.onSearchLaunched()
                if (gasoline.isEmpty()) {
                    listViewModel.getGasStationsByTowns()
                } else {
                    listViewModel.getGasStationsByTownsAndGasoline()
                }
                navController.navigate(Destinations.ListScreen.route)
            }
        )

        if (recentSearches.isNotEmpty()) {
            Spacer(Modifier.height(24.dp))
            Text(
                text = "ÚLTIMAS BÚSQUEDAS",
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 11.5.sp,
                letterSpacing = 0.1.em,
                color = Muted2
            )
            Spacer(Modifier.height(10.dp))
            recentSearches.forEach { search ->
                RecentSearchCard(search) {
                    searchViewModel.onRecentSearchClicked(search)
                    if (search.gasolineId.isEmpty()) {
                        listViewModel.getGasStationsByTowns()
                    } else {
                        listViewModel.getGasStationsByTownsAndGasoline()
                    }
                    navController.navigate(Destinations.ListScreen.route)
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        Spacer(Modifier.height(20.dp))
        AdBanner(
            adUnitId = "ca-app-pub-4979320410432560/7752668839",
            adSize = AdSize.MEDIUM_RECTANGLE
        )
        Spacer(Modifier.height(24.dp))
    }

    when (picker) {
        Picker.CARBURANTE -> {
            val opciones = listOf("Todos los carburantes") + gasolineList.map { it.nombreProducto }
            SelectorSheet(
                titulo = "Elige carburante",
                opciones = opciones,
                seleccionada = gasoline.ifEmpty { "Todos los carburantes" },
                onSeleccion = { indice ->
                    if (indice == 0) {
                        searchViewModel.onDismissGasoline()
                    } else {
                        val elegido = gasolineList[indice - 1]
                        searchViewModel.onGasolineSelected(elegido.iDProducto, elegido.nombreProducto)
                    }
                    picker = null
                },
                onDismiss = { picker = null }
            )
        }

        Picker.PROVINCIA -> SelectorSheet(
            titulo = "Elige provincia",
            opciones = provinceList.map { it.Provincia },
            seleccionada = province.ifEmpty { null },
            onSeleccion = { indice ->
                val elegida = provinceList[indice]
                searchViewModel.onProvinceSelected(elegida.Provincia, elegida.IDProvincia)
                picker = null
            },
            onDismiss = { picker = null }
        )

        Picker.MUNICIPIO -> SelectorSheet(
            titulo = "Elige municipio",
            opciones = townsList.map { it.Municipio },
            seleccionada = town.ifEmpty { null },
            onSeleccion = { indice ->
                val elegido = townsList[indice]
                searchViewModel.onTownSelected(elegido.Municipio, isTownSelected, elegido.IDMunicipio)
                picker = null
            },
            onDismiss = { picker = null }
        )

        null -> {}
    }
}

//AHORRO POR DEPOSITO = (PRECIO MAX - PRECIO MIN) * LITROS, CON LOS RESULTADOS DE LA ULTIMA BUSQUEDA
private fun calcularAhorro(
    gasolineSelected: String,
    gasList: List<com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorMunicipio>,
    gasListByGasAndTown: List<com.jarica.preciogasolina.data.network.Retrofit.response.GasolineraPorGasolinaYMunicipio>
): Double? {
    val precios: List<Double> = if (gasolineSelected.isNotEmpty() && gasListByGasAndTown.isNotEmpty()) {
        gasListByGasAndTown.mapNotNull { parsePrecio(it.precioProducto) }
    } else {
        gasList.mapNotNull { parsePrecio(it.precioGasoleoA) }
    }
    if (precios.size < 2) return null
    val ahorro = (precios.max() - precios.min()) * LITROS_DEPOSITO
    return if (ahorro > 0.005) ahorro else null
}

@Composable
private fun HeroAhorro(ahorro: Double?, municipio: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(NaranjaHero1, NaranjaHero2)))
    ) {
        //CIRCULOS DECORATIVOS TRANSLUCIDOS
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-45).dp)
                .size(130.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.12f))
        )
        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 20.dp, y = 35.dp)
                .size(90.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.10f))
        )
        Column(Modifier.padding(20.dp)) {
            Text(
                text = "Ahorra hasta",
                fontFamily = Sora,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = ahorro?.let {
                        String.format(Locale.forLanguageTag("es-ES"), "%.2f", it)
                    } ?: "9,50",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 46.sp,
                    lineHeight = 46.sp,
                    color = Color.White
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "€",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = if (ahorro != null && municipio.isNotEmpty()) {
                    "por depósito comparando precios en $municipio"
                } else {
                    "por depósito comparando precios antes de repostar"
                },
                fontFamily = Sora,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.92f)
            )
        }
    }
}

@Composable
private fun SelectorCard(
    icono: Int,
    etiqueta: String,
    valor: String,
    resaltado: Boolean = false,
    onClick: () -> Unit
) {
    val forma = RoundedCornerShape(18.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(forma)
            .background(Superficie)
            .border(1.5.dp, Linea, forma)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(NaranjaSuave),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icono),
                contentDescription = null,
                tint = Naranja,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = etiqueta,
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 10.5.sp,
                letterSpacing = 0.1.em,
                color = Muted2
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = valor,
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = if (resaltado) Muted2 else Ink,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = Muted3
        )
    }
}

@Composable
private fun SearchButton(habilitado: Boolean, onClick: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = onClick,
        enabled = habilitado,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Ink,
            contentColor = Color.White,
            disabledContainerColor = Ink.copy(alpha = 0.35f),
            disabledContentColor = Color.White.copy(alpha = 0.8f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = "Buscar gasolineras",
            fontFamily = Sora,
            fontWeight = FontWeight.Bold,
            fontSize = 16.5.sp
        )
    }
}

//TARJETA DE UNA BUSQUEDA RECIENTE: AL PULSARLA SE LANZA LA BUSQUEDA Y SE ABRE EL LISTADO
@Composable
private fun RecentSearchCard(search: RecentSearch, onClick: () -> Unit) {
    val forma = RoundedCornerShape(18.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(forma)
            .background(Superficie)
            .border(1.5.dp, Linea, forma)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(NaranjaSuave),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Naranja,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = search.gasolineName.ifEmpty { "Todos los carburantes" },
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Ink,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${search.townName} (${search.provinceName})",
                fontFamily = Sora,
                fontWeight = FontWeight.Medium,
                fontSize = 12.5.sp,
                color = Muted2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Muted3
        )
    }
}

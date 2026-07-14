package com.jarica.preciogasolina.ui.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.DeltaFondo
import com.jarica.preciogasolina.ui.theme.DeltaTexto
import com.jarica.preciogasolina.ui.theme.Fondo
import com.jarica.preciogasolina.ui.theme.Ink
import com.jarica.preciogasolina.ui.theme.InkSuave
import com.jarica.preciogasolina.ui.theme.Linea
import com.jarica.preciogasolina.ui.theme.Muted
import com.jarica.preciogasolina.ui.theme.Muted2
import com.jarica.preciogasolina.ui.theme.Muted3
import com.jarica.preciogasolina.ui.theme.Naranja
import com.jarica.preciogasolina.ui.theme.Sora
import com.jarica.preciogasolina.ui.theme.SpaceGrotesk
import com.jarica.preciogasolina.ui.theme.Superficie
import com.jarica.preciogasolina.ui.theme.Verde
import com.jarica.preciogasolina.ui.ui.model.FuelPrice
import com.jarica.preciogasolina.ui.ui.model.StationUi
import com.jarica.preciogasolina.ui.ui.model.formatDelta
import com.jarica.preciogasolina.ui.ui.model.formatPrecio

//TARJETA DE ESTACION UNIFICADA DEL REDISEÑO: SUSTITUYE A LAS ANTIGUAS
//cardStationByTowns Y CardStationByGasolineAndTown
@Composable
fun StationCard(
    station: StationUi,
    esMasBarata: Boolean = false,
    delta: Double? = null,
    esFavorita: Boolean = false,
    //EN BUSQUEDAS DE "TODOS LOS CARBURANTES" LA TARJETA LISTA TODOS LOS PRECIOS DISPONIBLES
    todosLosPrecios: Boolean = false,
    onClick: () -> Unit,
    onToggleFavorito: () -> Unit,
    modifier: Modifier = Modifier
) {
    val forma = RoundedCornerShape(20.dp)
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            Modifier
                .padding(top = 9.dp)
                .fillMaxWidth()
                .shadow(3.dp, forma, spotColor = Ink.copy(alpha = 0.22f))
                .clip(forma)
                .background(Superficie)
                .border(1.5.dp, Linea, forma)
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BrandAvatar(station.rotulo)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = station.rotulo,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = station.direccion,
                        fontFamily = Sora,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = Muted2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.width(8.dp))
                FavStar(esFavorita = esFavorita, onClick = onToggleFavorito)
            }
            Spacer(Modifier.height(13.dp))
            if (todosLosPrecios) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ChipDato(icono = R.drawable.ic_marker, texto = station.localidad)
                    ChipDato(
                        icono = R.drawable.ic_clock,
                        texto = station.horario,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                }
                if (station.precios.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(thickness = 1.dp, color = Linea)
                    Spacer(Modifier.height(6.dp))
                    GridPrecios(station.precios)
                }
            } else {
                Row(verticalAlignment = Alignment.Bottom) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            ChipDato(icono = R.drawable.ic_marker, texto = station.localidad)
                            ChipDato(
                                icono = R.drawable.ic_clock,
                                texto = station.horario,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                        }
                        if (delta != null && delta > 0.0005) {
                            ChipDelta(delta)
                        }
                    }
                    Spacer(Modifier.width(10.dp))
                    PrecioGrande(
                        precio = station.precio,
                        carburante = station.carburante,
                        enVerde = esMasBarata
                    )
                }
            }
        }
        if (esMasBarata) {
            BadgeMasBarata(Modifier.padding(start = 14.dp))
        }
    }
}

//PARRILLA DE 2 COLUMNAS CON TODOS LOS CARBURANTES DE LA ESTACION
@Composable
private fun GridPrecios(precios: List<FuelPrice>) {
    precios.chunked(2).forEach { fila ->
        Row(Modifier.fillMaxWidth()) {
            fila.forEach { fuel ->
                Row(
                    Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = fuel.nombre,
                        fontFamily = Sora,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = InkSuave,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "${formatPrecio(fuel.precio)} €",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        color = Ink
                    )
                    Spacer(Modifier.width(10.dp))
                }
            }
            if (fila.size == 1) {
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun BadgeMasBarata(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(Verde)
            .padding(horizontal = 9.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "★ MÁS BARATA",
            fontFamily = Sora,
            fontWeight = FontWeight.Bold,
            fontSize = 10.5.sp,
            letterSpacing = 0.06.em,
            color = Color.White
        )
    }
}

@Composable
fun FavStar(esFavorita: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(38.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = if (esFavorita) R.drawable.ic_starfilled_24 else R.drawable.ic_starempty_24
            ),
            contentDescription = if (esFavorita) "Quitar de favoritos" else "Añadir a favoritos",
            tint = if (esFavorita) Naranja else Muted3,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun ChipDato(icono: Int, texto: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Fondo)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icono),
            contentDescription = null,
            tint = Muted,
            modifier = Modifier.size(13.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = texto,
            fontFamily = Sora,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.5.sp,
            color = InkSuave,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ChipDelta(delta: Double) {
    Text(
        text = "${formatDelta(delta)} €/L",
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.5.sp,
        color = DeltaTexto,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(DeltaFondo)
            .padding(horizontal = 8.dp, vertical = 5.dp)
    )
}

@Composable
fun PrecioGrande(
    precio: Double?,
    carburante: String,
    enVerde: Boolean,
    modifier: Modifier = Modifier
) {
    val color = if (enVerde) Verde else Ink
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = precio?.let { formatPrecio(it) } ?: "—",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp,
                lineHeight = 27.sp,
                color = color
            )
            Spacer(Modifier.width(2.dp))
            Text(
                text = "€",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = color,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
        Text(
            text = carburante,
            fontFamily = Sora,
            fontWeight = FontWeight.Medium,
            fontSize = 10.5.sp,
            color = Muted2,
            maxLines = 1
        )
    }
}

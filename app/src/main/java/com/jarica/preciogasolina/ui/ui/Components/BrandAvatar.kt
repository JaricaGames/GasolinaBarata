package com.jarica.preciogasolina.ui.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.preciogasolina.R
import com.jarica.preciogasolina.ui.theme.ColoresMarca
import com.jarica.preciogasolina.ui.theme.Linea
import com.jarica.preciogasolina.ui.theme.Sora
import kotlin.math.abs

//LOGOS CONOCIDOS; SI EL ROTULO NO COINCIDE SE PINTA UN AVATAR CON INICIALES Y COLOR DE MARCA
private val logosPorRotulo = listOf(
    "REPSOL" to R.drawable.logorepsol,
    "CEPSA" to R.drawable.logocepsa,
    "SHELL" to R.drawable.logoshell,
    "GALP" to R.drawable.logogalp,
    "ALCAMPO" to R.drawable.logoalcampo,
    "BALLENOIL" to R.drawable.logoballenoil,
    "CAMPSA" to R.drawable.logocampsa,
    "CARREFOUR" to R.drawable.logocarrefour,
    "NATURGY" to R.drawable.logonaturgy,
    "PETROGOLD" to R.drawable.logopetrogold,
    "PLENOIL" to R.drawable.logoplenoil,
    "SARAS" to R.drawable.logosaras,
    "SETTRAN" to R.drawable.logosettran,
    "SUPECO" to R.drawable.logosupeco,
    "Q8" to R.drawable.logoq8,
    "BP" to R.drawable.logobp
)

private fun logoDeMarca(rotulo: String): Int? {
    val nombre = rotulo.uppercase()
    return logosPorRotulo.firstOrNull { nombre.contains(it.first) }?.second
}

private fun iniciales(rotulo: String): String {
    val palabras = rotulo.trim().split(Regex("\\s+")).filter { it.isNotBlank() }
    return when {
        palabras.size >= 2 -> "${palabras[0].first()}${palabras[1].first()}".uppercase()
        rotulo.length >= 2 -> rotulo.take(2).uppercase()
        else -> rotulo.uppercase()
    }
}

private fun colorDeMarca(rotulo: String): Color =
    ColoresMarca[abs(rotulo.hashCode()) % ColoresMarca.size]

@Composable
fun BrandAvatar(rotulo: String, modifier: Modifier = Modifier, size: Dp = 46.dp) {
    val logo = logoDeMarca(rotulo)
    val forma = RoundedCornerShape(14.dp)
    if (logo != null) {
        Box(
            modifier = modifier
                .size(size)
                .clip(forma)
                .background(Color.White)
                .border(1.dp, Linea, forma),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = logo),
                contentDescription = rotulo,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )
        }
    } else {
        Box(
            modifier = modifier
                .size(size)
                .clip(forma)
                .background(colorDeMarca(rotulo)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = iniciales(rotulo),
                fontFamily = Sora,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

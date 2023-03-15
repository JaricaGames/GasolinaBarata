package com.jarica.preciogasolina.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Naranja,
    primaryVariant = Naranja,
    secondary = NaranjaClaro
)

private val LightColorPalette = lightColors(
    primary = Naranja,
    primaryVariant = Beige,
    secondary = Naranja,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.Gray,
    onSecondary = Color.Gray,
    onBackground = Color.Black,
    onSurface = Color.Black,

)

@Composable
fun PrecioGasolinaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
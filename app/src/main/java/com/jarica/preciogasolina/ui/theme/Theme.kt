package com.jarica.preciogasolina.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// El rediseño define una única apariencia clara (fondo cálido #F5F2EC)
private val LightColorScheme = lightColorScheme(
    primary = Naranja,
    onPrimary = Color.White,
    primaryContainer = NaranjaSuave,
    onPrimaryContainer = Naranja,
    secondary = Verde,
    onSecondary = Color.White,
    secondaryContainer = VerdeFondo,
    onSecondaryContainer = Verde,
    background = Fondo,
    onBackground = Ink,
    surface = Superficie,
    onSurface = Ink,
    surfaceVariant = NaranjaSuave,
    onSurfaceVariant = InkSuave,
    outline = Linea,
    outlineVariant = Linea
)

@Composable
fun PrecioGasolinaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

package com.jarica.preciogasolina.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.preciogasolina.R

// Sora: UI y texto. Fuente variable, el peso se resuelve por eje wght.
val Sora = FontFamily(
    Font(R.font.sora_variable, FontWeight.Normal),
    Font(R.font.sora_variable, FontWeight.Medium),
    Font(R.font.sora_variable, FontWeight.SemiBold),
    Font(R.font.sora_variable, FontWeight.Bold),
    Font(R.font.sora_variable, FontWeight.ExtraBold)
)

// Space Grotesk: precios y números.
val SpaceGrotesk = FontFamily(
    Font(R.font.space_grotesk_variable, FontWeight.Medium),
    Font(R.font.space_grotesk_variable, FontWeight.SemiBold),
    Font(R.font.space_grotesk_variable, FontWeight.Bold)
)

val Typography = Typography(
    // H1 del buscador
    headlineLarge = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 31.sp,
        lineHeight = 33.sp,
        letterSpacing = (-0.025).em
    ),
    // Título de sección (Gasolineras, Favoritos)
    titleLarge = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        letterSpacing = (-0.015).em
    ),
    // Nombre de estación en tarjeta
    titleMedium = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.Bold,
        fontSize = 15.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    ),
    // Etiqueta en mayúsculas
    labelMedium = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.08.em
    ),
    // Label de tab
    labelSmall = TextStyle(
        fontFamily = Sora,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp
    )
)

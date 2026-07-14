package com.jarica.preciogasolina.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),   // chips cuadrados (distancia/horario)
    small = RoundedCornerShape(14.dp),       // chips rápidos, avatar marca
    medium = RoundedCornerShape(18.dp),      // selectores y botones CTA
    large = RoundedCornerShape(20.dp),       // tarjetas de estación
    extraLarge = RoundedCornerShape(28.dp)   // bottom-sheet (esquinas superiores)
)

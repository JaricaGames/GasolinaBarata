package com.jarica.preciogasolina.ui.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.preciogasolina.ui.theme.Ink
import com.jarica.preciogasolina.ui.theme.Muted
import com.jarica.preciogasolina.ui.theme.Naranja
import com.jarica.preciogasolina.ui.theme.NaranjaSuave
import com.jarica.preciogasolina.ui.theme.Sora

//ESTADO VACIO DEL REDISEÑO: CIRCULO NARANJA SUAVE + TITULO + TEXTO + CTA
@Composable
fun EmptyState(
    icono: @Composable () -> Unit,
    titulo: String,
    texto: String,
    textoBoton: String,
    onBoton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(NaranjaSuave),
            contentAlignment = Alignment.Center
        ) {
            icono()
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = titulo,
            fontFamily = Sora,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = Ink,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = texto,
            fontFamily = Sora,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Muted,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onBoton,
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Naranja,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text(
                text = textoBoton,
                fontFamily = Sora,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

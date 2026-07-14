package com.jarica.preciogasolina.ui.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.preciogasolina.ui.theme.Ink
import com.jarica.preciogasolina.ui.theme.Linea
import com.jarica.preciogasolina.ui.theme.Naranja
import com.jarica.preciogasolina.ui.theme.Sora
import com.jarica.preciogasolina.ui.theme.Superficie

//BOTTOM-SHEET DE SELECCION (CARBURANTE / PROVINCIA / MUNICIPIO) DEL REDISEÑO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorSheet(
    titulo: String,
    opciones: List<String>,
    seleccionada: String?,
    onSeleccion: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Superficie,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = { Grabber() }
    ) {
        Text(
            text = titulo,
            fontFamily = Sora,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 19.sp,
            color = Ink,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 6.dp)
        )
        LazyColumn(
            modifier = Modifier
                .heightIn(max = 440.dp)
                .navigationBarsPadding()
        ) {
            itemsIndexed(opciones) { indice, opcion ->
                val activa = opcion == seleccionada
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSeleccion(indice) }
                        .padding(horizontal = 22.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = opcion,
                        fontFamily = Sora,
                        fontWeight = if (activa) FontWeight.Bold else FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = if (activa) Naranja else Ink,
                        modifier = Modifier.weight(1f)
                    )
                    if (activa) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Seleccionado",
                            tint = Naranja,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Grabber() {
    Box(
        Modifier
            .padding(top = 10.dp, bottom = 8.dp)
            .width(42.dp)
            .size(width = 42.dp, height = 4.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(Linea)
    )
}

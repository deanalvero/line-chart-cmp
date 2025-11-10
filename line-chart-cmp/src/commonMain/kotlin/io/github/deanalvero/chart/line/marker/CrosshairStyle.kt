package io.github.deanalvero.chart.line.marker

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CrosshairStyle(
    val color: Color = Color.Companion.Gray,
    val strokeWidth: Dp = 1.dp,
    val pathEffect: PathEffect? = null
)